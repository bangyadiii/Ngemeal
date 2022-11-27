package com.ngemeal.ngemeal.ui.auth.signup

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.ngemeal.ngemeal.Ngemeal
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.databinding.FragmentSignUpAddressBinding
import com.ngemeal.ngemeal.model.request.RegisterRequest
import com.ngemeal.ngemeal.model.response.login.LoginResponse
import com.ngemeal.ngemeal.ui.MainActivity
import com.ngemeal.ngemeal.ui.auth.AuthActivity
import com.ngemeal.ngemeal.ui.auth.signin.SignInContract


class SignUpAddressFragment : Fragment(), SignUpContract.View {

    private var _binding : FragmentSignUpAddressBinding? = null
    private val binding get() = _binding!!
    private lateinit var data: RegisterRequest
    private lateinit var presenter: SignUpPresenter
    var progressDialog : Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.presenter  = SignUpPresenter(this)
        this._binding = FragmentSignUpAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AuthActivity).toolBarSignUpAddress()
        data = arguments?.getParcelable<RegisterRequest>("data")!!
        initListener()
        initView()
    }


    private fun initListener() {
        binding.btnSignUpNow.setOnClickListener{
            var phone = binding.etPhoneNumber.text.toString()
            var address = binding.etAlamat.text.toString()
            var houseNumber = binding.etHouseNumber.text.toString()
            var city = binding.etCity.text.toString()
            validateData(phone, address, houseNumber, city)

            data.let {
                it.phoneNumber = phone
                it.houseNumber = houseNumber
                it.address = address
                it.city = city
            }
            presenter.submitRegister(this.data, it)
        }
    }

    private fun validateData(phone: String, address : String, houseNumber : String, city : String)  {
        if(phone.isNullOrEmpty()) {
            binding.etPhoneNumber.error = "Masukkan phone number"
        }else if(address.isNullOrEmpty()) {
            binding.etAlamat.error = "Masukkan alamat kamu"
        }else if(houseNumber.isNullOrEmpty()) {
            binding.etHouseNumber.error = "Masukan house number"
        }else if(city.isNullOrEmpty()) {
            binding.etCity.error = "Masukan kota kamu"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
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

    override fun onRegisterSuccess(registerResponse: LoginResponse, view: View) {
        Ngemeal.getApp().setToken(registerResponse.access_token)

        val gson =  Gson()
        val json =  gson.toJson(registerResponse.user)
        Ngemeal.getApp().setUser(json)
        if(data.filePath == null) {
            Navigation.findNavController(view)
                .navigate(R.id.action_fragmentSignUpAddress_to_signUpSuccessFragment, null)
            (activity as AuthActivity).toolbarSignUpSuccess()
        }else {
            presenter.submitPhotoAvatar(data.filePath!!, view)
        }
    }

    override fun onRegisterPhotoSuccess(view: View) {
        Navigation.findNavController(view)
            .navigate(R.id.action_fragmentSignUpAddress_to_signUpSuccessFragment, null)
        (activity as AuthActivity).toolbarSignUpSuccess()
    }


    override fun onRegisterFailed(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        Log.d("register", message)
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }


}