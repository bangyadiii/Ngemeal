package com.ngemeal.ngemeal.ui.home.popular

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.databinding.FragmentHomeNewTasteBinding
import com.ngemeal.ngemeal.model.dummy.HomeVerticalModel
import com.ngemeal.ngemeal.model.response.home.Data
import com.ngemeal.ngemeal.model.response.home.HomeResponse
import com.ngemeal.ngemeal.ui.detail.DetailActivity
import com.ngemeal.ngemeal.ui.home.newtaste.HomeNewTasteContract
import com.ngemeal.ngemeal.ui.home.newtaste.HomeNewTastePresenter
import com.ngemeal.ngemeal.ui.home.newtaste.HomeNewtasteAdapter

class HomePopularFragment : Fragment() ,HomeNewtasteAdapter.ItemAdapterCallback, HomeNewTasteContract.View {
    private var foodList : ArrayList<HomeVerticalModel> = ArrayList()

    private lateinit var presenter: HomeNewTastePresenter
    private var _binding: FragmentHomeNewTasteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeNewTasteBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
        //return inflater.inflate(R.layout.fragment_home_new_taste, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = HomeNewTastePresenter(this)
        presenter.getHome("populer")
    }

    fun initDataDummy(){
        foodList = ArrayList()
        foodList.add(HomeVerticalModel("Cherry", "100000","",5f))
        foodList.add(HomeVerticalModel("Burger", "200000","",4f))
        foodList.add(HomeVerticalModel("Cherry", "300000","",4.5f))

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View, data: Data) {
        val detail = Intent(activity, DetailActivity::class.java)
        startActivity(detail)
    }

    override fun onHomeSuccess(homeResponse: HomeResponse) {
        var adapter = HomeNewtasteAdapter(homeResponse.data,this)
        var layoutManager: RecyclerView.LayoutManager= LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
        binding.rcList.layoutManager = layoutManager
        binding.rcList.adapter = adapter
    }

    override fun onHomeFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.shimmerHomeVertical.startShimmer()
        binding.shimmerHomeVertical.visibility = View.VISIBLE
        binding.rcList.visibility = View.GONE
    }

    override fun dismissLoading() {
        binding.shimmerHomeVertical.stopShimmer()
        binding.shimmerHomeVertical.visibility = View.GONE
        binding.rcList.visibility = View.VISIBLE
    }

}