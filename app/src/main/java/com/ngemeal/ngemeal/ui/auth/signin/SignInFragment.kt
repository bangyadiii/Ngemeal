package com.ngemeal.ngemeal.ui.auth.signin

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import com.ngemeal.ngemeal.Ngemeal
import com.ngemeal.ngemeal.ui.MainActivity
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.databinding.FragmentSignInBinding
import com.ngemeal.ngemeal.model.response.login.LoginResponse
import com.ngemeal.ngemeal.ui.auth.AuthActivity


/**
 * A simple [Fragment] subclass.
 * Use the [SignInFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignInFragment : Fragment(), SignInContract.View {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: SignInContract.Presenter
    private var progressDialog : Dialog? = null


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
        presenter = SignInPresenter(this)

        if(!Ngemeal.getApp().getToken().isNullOrEmpty()) {
            val home = Intent(activity, MainActivity::class.java)
            startActivity(home)
            activity?.finish();
        }

        initView()

        (activity as AuthActivity).toolBarSignIn()

        binding.btnCreateAccount.setOnClickListener{
            val signUp = Intent(activity, AuthActivity::class.java)
            signUp.putExtra("page_request", 2)
            startActivity(signUp)
        }

        binding.btnSignIn.setOnClickListener{
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val deviceName = android.os.Build.MODEL;

            if(email.isNullOrEmpty()) {
                binding.etEmail.error = "Masukkan email anda"
                binding.etEmail.requestFocus()
            }else if(password.isNullOrEmpty()) {
                binding.etPassword.error = "Masukkan password anda"
                binding.etPassword.requestFocus()
            }else {
                presenter.submitLogin(email, password, deviceName)
            }
        }

    }

    fun initView() {
        this.progressDialog = Dialog(requireContext())
        val dialogLayout  = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onLoginSuccess(loginResponse: LoginResponse) {
        Ngemeal.getApp().setToken(loginResponse.access_token)

        val gson =  Gson()
        val json =  gson.toJson(loginResponse.user)
        Ngemeal.getApp().setUser(json)

        val home = Intent(activity, MainActivity::class.java)
        startActivity(home)
        activity?.finish();
    }

    override fun onLoginFailed(message: String, errors: HashMap<String, List<String>>?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        if(errors != null) {
            errors["email"]?.let {
                binding.etEmail.error = it[0]
                binding.etEmail.requestFocus()
            }
            errors["password"]?.let {
                binding.etPassword.error = it[0]
            }
        }
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

}