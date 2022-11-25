package com.ngemeal.ngemeal.ui.home.newtaste

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.databinding.FragmentHomeBinding
import com.ngemeal.ngemeal.databinding.FragmentHomeNewTasteBinding
import com.ngemeal.ngemeal.model.dummy.HomeModel
import com.ngemeal.ngemeal.model.dummy.HomeVerticalModel
import com.ngemeal.ngemeal.ui.home.HomeAdapter
import com.ngemeal.ngemeal.ui.home.SectionPagerAdapter

class HomeNewTasteFragment : Fragment(),HomeNewtasteAdapter.ItemAdapterCallback {
    private var foodList : ArrayList<HomeVerticalModel> = ArrayList()

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

        initDataDummy()
        var adapter = HomeNewtasteAdapter(foodList,this)
        var layoutManager: RecyclerView.LayoutManager= LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
        binding.rcList.layoutManager = layoutManager
        binding.rcList.adapter = adapter
    }

    fun initDataDummy(){
        foodList = ArrayList()
        foodList.add(HomeVerticalModel("Cherry", "100000","",5f))
        foodList.add(HomeVerticalModel("Burger", "200000","",4f))
        foodList.add(HomeVerticalModel("Cherry", "300000","",4.5f))

    }

    override fun onClick(v: View, data: HomeVerticalModel) {
        Toast.makeText(context,"percobaan "+data.title, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}