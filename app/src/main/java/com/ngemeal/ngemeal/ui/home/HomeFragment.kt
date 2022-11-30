package com.ngemeal.ngemeal.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.databinding.FragmentHomeBinding
import com.ngemeal.ngemeal.model.dummy.HomeModel
import com.ngemeal.ngemeal.model.response.home.Data
import com.ngemeal.ngemeal.model.response.home.HomeResponse
import com.ngemeal.ngemeal.ui.auth.signup.SignUpPresenter

class HomeFragment : Fragment(), HomeAdapter.ItemAdapterCallback, HomeContract.View {

    private var foodList : ArrayList<Data> = ArrayList()
    private lateinit var presenter: HomePresenter

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    var progressDialog : Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        presenter = HomePresenter(this)
        presenter.getHome()
        initView()
        val root: View = binding.root
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        presenter = HomePresenter(this)
//        presenter.getHome()
//        initView()
//        initDataDummy()
    }

    private fun initView() {
        this.progressDialog = Dialog(requireContext())
        val dialogLayout  = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

//    fun initDataDummy(){
//        foodList = ArrayList()
//        foodList.add(HomeModel("Cherry", "",5f))
//        foodList.add(HomeModel("Burger", "",4f))
//        foodList.add(HomeModel("Cherry", "",4.5f))
//
//    }

    override fun onClick(v: View, data: Data) {
        Toast.makeText(context,"test click",Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onHomeSuccess(homeResponse: HomeResponse) {
        var adapter = HomeAdapter(homeResponse.data,this)
        var layoutManager: RecyclerView.LayoutManager= LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding.rcList.layoutManager = layoutManager
        binding.rcList.adapter = adapter

        val sectionPagerAdapter = SectionPagerAdapter(
            childFragmentManager
        )
        binding.viewPager.adapter = sectionPagerAdapter
        binding.tabsLayout.setupWithViewPager(binding.viewPager)
    }

    override fun onHomeFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}