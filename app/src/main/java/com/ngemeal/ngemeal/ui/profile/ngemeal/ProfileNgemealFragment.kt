package com.ngemeal.ngemeal.ui.profile.ngemeal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.databinding.FragmentProfileAccountBinding
import com.ngemeal.ngemeal.model.dummy.ProfileModel
import com.ngemeal.ngemeal.ui.profile.ProfileMenuAdapter

class ProfileNgemealFragment : Fragment(),ProfileMenuAdapter.ItemAdapterCallback{

    private var menuArrayList: ArrayList<ProfileModel> = ArrayList()

    private var _binding: FragmentProfileAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
        //return inflater.inflate(R.layout.fragment_profile_account, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initDataDummy()

        var adapter = ProfileMenuAdapter(menuArrayList, this)
        var layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(activity)
        binding.rcList.layoutManager = layoutManager
        binding.rcList.adapter = adapter
    }
    fun initDataDummy(){
        menuArrayList = ArrayList()
        menuArrayList.add(ProfileModel("Nilai Aplikasi"))
        menuArrayList.add(ProfileModel("Pusat Bantuan"))
        menuArrayList.add(ProfileModel("Kebijakan Privasi"))
        menuArrayList.add(ProfileModel("Terms & Conditions"))

    }

    override fun onClick(v: View, data: ProfileModel) {
        Toast.makeText(context,"test click "+data.title, Toast.LENGTH_LONG).show()
    }

}