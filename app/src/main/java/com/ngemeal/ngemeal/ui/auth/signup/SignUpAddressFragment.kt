package com.ngemeal.ngemeal.ui.auth.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.databinding.FragmentSignUpAddressBinding
import com.ngemeal.ngemeal.ui.auth.AuthActivity


class SignUpAddressFragment : Fragment() {

    private var _binding : FragmentSignUpAddressBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this._binding = FragmentSignUpAddressBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AuthActivity).toolBarSignUpAddress()

        binding.btnSignUpNow.setOnClickListener{
            Navigation.findNavController(it)
                .navigate(R.id.action_fragmentSignUpAddress_to_signUpSuccessFragment, null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }


}