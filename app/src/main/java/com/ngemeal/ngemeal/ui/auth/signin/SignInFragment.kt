package com.ngemeal.ngemeal.ui.auth.signin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ngemeal.ngemeal.ui.MainActivity
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.databinding.FragmentSignInBinding
import com.ngemeal.ngemeal.ui.auth.AuthActivity


/**
 * A simple [Fragment] subclass.
 * Use the [SignInFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_sign_in,container,false);
        this._binding = FragmentSignInBinding.inflate(layoutInflater, container, false)
        return this.binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AuthActivity).toolBarSignIn()

        binding.btnCreateAccount.setOnClickListener{
            val signUp = Intent(activity, AuthActivity::class.java)
            signUp.putExtra("page_request", 2)
            startActivity(signUp)
        }

        binding.btnSignIn.setOnClickListener{
            val home = Intent(activity, MainActivity::class.java)
            startActivity(home)
            activity?.finish();
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}