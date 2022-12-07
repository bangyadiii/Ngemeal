package com.ngemeal.ngemeal.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.databinding.FragmentDetailBinding
import com.ngemeal.ngemeal.model.response.home.Data
import com.ngemeal.ngemeal.utils.Helpers.formatPrice

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val data : DetailFragmentArgs by navArgs()
    var bundle : Bundle ?  = null
    
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as DetailActivity).toolbarDetail()
        data.data?.let {
            Log.d("dataass", it.toString())
           initView(it)
        }


    }

    private fun initView(data : Data){
        bundle = bundleOf("data" to data)
        data.let {
            Glide.with(requireContext())
                .load(data.images?.get(0)?.imageUrl)
                .centerCrop()
                .into(binding.imgbg)
            binding.tvJudulDisini.text = data.name
            binding.tvDesc.text = data.description
            binding.tvIngredients.text = data.ingredients
            binding.tvTotal.formatPrice(data.price!!.toString())
        }

        binding.btnOrderNow.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_payment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}