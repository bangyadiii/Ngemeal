package com.ngemeal.ngemeal.ui.order.pastorders

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
import com.ngemeal.ngemeal.databinding.FragmentInProgressBinding
import com.ngemeal.ngemeal.databinding.FragmentPastProgressBinding
import com.ngemeal.ngemeal.model.response.PaginateResponse
import com.ngemeal.ngemeal.model.response.transaction.Transaction
import com.ngemeal.ngemeal.ui.order.OrderContract
import com.ngemeal.ngemeal.ui.order.OrderPresenter
import com.ngemeal.ngemeal.ui.order.inprogress.InProgressAdapter
import com.ngemeal.ngemeal.ui.order.inprogress.InProgressPresenter

class PastOrdersFragment : Fragment(), PastOrderAdapter.ItemAdapterCallback, OrderContract.View {

    private var _binding: FragmentPastProgressBinding? = null
    private val binding get() = _binding!!
    private var adapter : PastOrderAdapter? = null
    private var pastOrderList : List<Transaction>? = ArrayList()
    var progressDialog : Dialog? = null
    private  lateinit var presenter: PastOrderPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPastProgressBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = PastOrderPresenter(this)

        if (Build.VERSION.SDK_INT >= 33) {
            pastOrderList = arguments?.getParcelableArrayList("data", Transaction::class.java)
        } else {
            pastOrderList = arguments?.getParcelableArrayList<Transaction>("data")
        }

        if(!pastOrderList.isNullOrEmpty()) {
            adapter = PastOrderAdapter(pastOrderList!!, this)
            var layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(activity)
            binding.rcList.layoutManager = layoutManager
            binding.rcList.adapter = adapter
        }else{
            presenter.getTransaction(paymentStatus = "success", limit = 20, orderBy = "latest")
        }

    }


    override fun onClick(v: View, data: Transaction) {
        Toast.makeText(requireActivity(), "Tsest clik", Toast.LENGTH_SHORT).show()
    }

    override fun onTransactionSuccess(transactionResponse: PaginateResponse<Transaction>) {
        pastOrderList = transactionResponse.data
        adapter = PastOrderAdapter(pastOrderList!!, this)
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