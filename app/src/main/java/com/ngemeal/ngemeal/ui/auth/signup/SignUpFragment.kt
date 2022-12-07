package com.ngemeal.ngemeal.ui.auth.signup

import android.app.Activity
import android.app.Dialog
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.common.oob.SignUp
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.databinding.FragmentSignUpBinding
import com.ngemeal.ngemeal.model.request.RegisterRequest
import com.ngemeal.ngemeal.model.response.login.LoginResponse
import com.ngemeal.ngemeal.model.response.signup.CheckEmail
import com.ngemeal.ngemeal.ui.auth.AuthActivity
import com.ngemeal.ngemeal.ui.auth.signin.SignInContract


class SignUpFragment : Fragment(), CheckEmailContract.View {
    private var _binding : FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    var filePath : Uri ? = null
    private var data : RegisterRequest? = null
    var progressDialog : Dialog? = null
    private lateinit var presenter : SignUpPrimaryPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this._binding = FragmentSignUpBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AuthActivity).toolBarSignUp()
        initView()
        initListener()
        presenter = SignUpPrimaryPresenter(this)

        binding.btnSignUpContinue.setOnClickListener{
            var name = binding.etNamaLengkap.text.toString()
            var email = binding.etEmail.text.toString()
            var password = binding.etPassword.text.toString()
            var passConf = binding.etPasswordConf.text.toString()

            validateInput(it, name, email, password, passConf)

        }
    }

    private fun initListener() {
        binding.ivAvatar.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1024,1024)
                .galleryMimeTypes(  //Exclude gif images
                    mimeTypes = arrayOf(
                        "image/png",
                        "image/jpg",
                        "image/jpeg"
                    )
                )
                .start()
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

    private fun validateInput(it : View, name : String, email : String, password : String, passwordConf: String) {
        if(name.isNullOrEmpty())  {
            binding.etNamaLengkap.setError("Masukkan nama kamu")
        }
        else if(email.isNullOrEmpty())  {
            binding.etNamaLengkap.setError("Masukkan email")
        }
        else if(password.isNullOrEmpty())  {
            binding.etNamaLengkap.setError("Masukkan password")
        }
        else if (!password.equals(passwordConf)) {
            binding.etPasswordConf.setError("Password tidak sama")
        }else {
            this.data = RegisterRequest(
                name,
                email,
                password,
                passwordConf,
                "",
                this.filePath,
                "", "", ""
            )
            presenter.checkEmailAvailable(email, it)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            this.filePath = data?.data

            Glide.with(this)
                .load(filePath)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivAvatar)

        }else if(resultCode == ImagePicker.RESULT_ERROR){
            Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(context, "Tast cancelled", Toast.LENGTH_LONG).show()
        }
    }


    override fun onCheckEmailSuccess(response: CheckEmail, view: View) {
        if(!response.isEmailAvailable!!) {
            binding.etEmail.error = "Email ini sudah digunakan"
            binding.etEmail.requestFocus()
        }else{
            var bundler = Bundle()
            bundler.putParcelable("data", data)

            Navigation.findNavController(view)
                .navigate(R.id.action_fragmentSignUp_to_fragmentSignUpAddress, bundler)
        }

    }

    override fun onCheckEmailFailed(message: String, errors : Map<String, List<String>>?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        Log.d("check-email", errors.toString())
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}