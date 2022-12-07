package com.ngemeal.ngemeal.ui.order

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.databinding.FragmentOrdersBinding
import com.ngemeal.ngemeal.model.response.PaginateResponse
import com.ngemeal.ngemeal.model.response.transaction.Transaction

class OrderFragment : Fragment(), OrderContract.View {

    private var _binding: FragmentOrdersBinding? = null
    var progressDialog : Dialog? = null
    private var user : String? = null
    private var inProgressList : ArrayList<Transaction>? =null
    private var pastOrderList : ArrayList<Transaction>? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var presenter: OrderPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        presenter = OrderPresenter(this)
        presenter.getTransaction(limit = 20)
    }

    private fun initView(){
        this.progressDialog = Dialog(requireContext())
        val dialogLayout  = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        binding.btnFindMakan.setOnClickListener {
            var navController = Navigation.findNavController(it)

            navController.navigate(R.id.action_navigation_order_to_navigation_home)
        }
    }

    override fun onTransactionSuccess(transactionRes: PaginateResponse<Transaction>) {
        if(transactionRes.data.isNullOrEmpty()) {
            binding.llEmpty.visibility = View.VISIBLE
            binding.orderList.visibility = View.GONE
            binding.iniToolbar1.toolbar.visibility = View.GONE
        }else {
            binding.llEmpty.visibility = View.GONE
            binding.orderList.visibility = View.VISIBLE
            binding.iniToolbar1.toolbar.visibility = View.VISIBLE

            inProgressList = ArrayList()
            pastOrderList = ArrayList()

            for(a in transactionRes.data.indices){
                if(transactionRes.data?.get(a)?.deliveryStatus.equals("waiting_driver", true)
                    || transactionRes.data?.get(a)?.deliveryStatus.equals("on_delivery", true)
                    || transactionRes.data?.get(a)?.deliveryStatus.equals("cancelled", true)
                    || transactionRes.data?.get(a)?.deliveryStatus.equals("pending", true)
                ){
                    inProgressList!!.add(transactionRes.data.get(a))
                }else if( transactionRes.data?.get(a)?.deliveryStatus.equals("success", true)
                    || transactionRes.data?.get(a)?.deliveryStatus.equals("finish", true)
                ){
                    pastOrderList!!.add(transactionRes.data.get(a))
                }
            }

            val sectionsPagerAdapter = SectionPagerAdapter(childFragmentManager)

            sectionsPagerAdapter.setData(inProgressList, pastOrderList)
            binding.viewPager.adapter = sectionsPagerAdapter
            binding.tabsLayout.setupWithViewPager(binding.viewPager)
        }
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