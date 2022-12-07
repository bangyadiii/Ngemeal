package com.ngemeal.ngemeal.ui.order.inprogress

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.databinding.FragmentInProgressBinding
import com.ngemeal.ngemeal.model.response.PaginateResponse
import com.ngemeal.ngemeal.model.response.transaction.Transaction
import com.ngemeal.ngemeal.ui.order.OrderContract
import com.ngemeal.ngemeal.ui.order.SectionPagerAdapter

class InProgressFragment : Fragment(), InProgressAdapter.ItemAdapterCallback, OrderContract.View {

    private var inProgressList : List<Transaction>? = ArrayList()
    private var _binding: FragmentInProgressBinding? = null
    private val binding get() = _binding!!
    private var adapter : InProgressAdapter? = null
    var progressDialog : Dialog? = null
    private  lateinit var presenter: InProgressPresenter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInProgressBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        presenter = InProgressPresenter(this)

        if (Build.VERSION.SDK_INT >= 33) {
           inProgressList = arguments?.getParcelableArrayList("data", Transaction::class.java)
        } else {
           inProgressList = arguments?.getParcelableArrayList<Transaction>("data")
        }

        if(!inProgressList.isNullOrEmpty()) {
            adapter = InProgressAdapter(inProgressList!!, this)
            var layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(activity)
            binding.rcList.layoutManager = layoutManager
            binding.rcList.adapter = adapter
        }else{
            presenter.getTransaction(paymentStatus = "success" ,limit = 20, orderBy = "latest")
        }
    }


    override fun onClick(v: View, data: Transaction) {
        Toast.makeText(context,"test click "+data.food?.name, Toast.LENGTH_LONG).show()
    }

    private fun initView(){
        this.progressDialog = Dialog(requireContext())
        val dialogLayout  = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onTransactionSuccess(transactionRes: PaginateResponse<Transaction>) {
        inProgressList = transactionRes.data
        adapter = InProgressAdapter(inProgressList!!, this)
        var layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(activity)
        binding.rcList.layoutManager = layoutManager
        binding.rcList.adapter = adapter
    }

    override fun onTransactionFailed(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}