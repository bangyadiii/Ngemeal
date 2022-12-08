package com.ngemeal.ngemeal.ui.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.ngemeal.ngemeal.Ngemeal
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.databinding.FragmentHomeBinding
import com.ngemeal.ngemeal.model.response.home.Data
import com.ngemeal.ngemeal.model.response.PaginateResponse
import com.ngemeal.ngemeal.model.response.login.User
import com.ngemeal.ngemeal.ui.detail.DetailActivity

class HomeFragment : Fragment(), HomeAdapter.ItemAdapterCallback, HomeContract.View {

    private var foodList : ArrayList<Data> = ArrayList()
    private lateinit var presenter: HomePresenter

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    var progressDialog : Dialog? = null
    private var user : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val sectionPagerAdapter = SectionPagerAdapter(
            childFragmentManager
        )
        binding.viewPager.adapter = sectionPagerAdapter
        binding.tabsLayout.setupWithViewPager(binding.viewPager)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = HomePresenter(this)
        presenter.getHome()

        initView()
    }

    private fun initView() {
        user = Ngemeal.getApp().getUser()
        var userResponse = Gson().fromJson(user, User::class.java)
        if(!userResponse.profile_photo_url.isNullOrEmpty()){
            Glide.with(requireContext())
                .load(userResponse.profile_photo_url)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivProfile)
        }
        this.progressDialog = Dialog(requireContext())
        val dialogLayout  = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }


    override fun onClick(v: View, data: Data) {
        val detail = Intent(activity, DetailActivity::class.java).putExtra("data", data )
        startActivity(detail)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onHomeSuccess(homeResponse: PaginateResponse<Data>) {
        var adapter = HomeAdapter(homeResponse.data,this)
        var layoutManager: RecyclerView.LayoutManager= LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding.rcList.layoutManager = layoutManager
        binding.rcList.adapter = adapter

    }

    override fun onHomeFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.rcList.visibility = View.GONE
        binding.shimmerHomeHorizontal.startShimmer()
        binding.shimmerHomeHorizontal.visibility = View.VISIBLE
//        progressDialog?.show()
    }

    override fun dismissLoading() {
        binding.shimmerHomeHorizontal.stopShimmer()
        binding.shimmerHomeHorizontal.visibility = View.GONE
        binding.rcList.visibility = View.VISIBLE
//        progressDialog?.dismiss()
    }
}