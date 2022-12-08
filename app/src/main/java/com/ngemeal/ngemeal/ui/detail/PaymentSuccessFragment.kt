package com.ngemeal.ngemeal.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.databinding.FragmentPaymentSuccessBinding

class PaymentSuccessFragment : Fragment() {
    private var _binding : FragmentPaymentSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPaymentSuccessBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as DetailActivity).toolbarDetail()
        binding.btnMyOrder.setOnClickListener{
            requireActivity().finish()
        }
        binding.btnOtherFood.setOnClickListener{
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}