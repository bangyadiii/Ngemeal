package com.ngemeal.ngemeal.ui.auth.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.databinding.FragmentSignUpBinding
import com.ngemeal.ngemeal.databinding.FragmentSignUpSuccessBinding
import com.ngemeal.ngemeal.ui.auth.AuthActivity


class SignUpSuccessFragment : Fragment() {
    private var _binding : FragmentSignUpSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this._binding = FragmentSignUpSuccessBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AuthActivity).toolbarSignUpSuccess()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}