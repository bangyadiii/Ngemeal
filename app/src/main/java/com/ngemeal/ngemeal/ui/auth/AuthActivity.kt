package com.ngemeal.ngemeal.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private var _binding : ActivityAuthBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this._binding = ActivityAuthBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val pageReq = intent.getIntExtra("page_request", 0)

        if (pageReq == 2) {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.fragmentSignIn, true)
                .build()
            Navigation.findNavController(findViewById(R.id.auth_navigation))
                .navigate(R.id.action_fragmentSignIn_to_fragmentSignUp, null, navOptions)
        }
    }
    fun toolBarSignIn() {
        this.binding.toolbarAuth.toolbar.title = "Sign In"
        this.binding.toolbarAuth.toolbar.subtitle = "Find your best ever meal"
    }

    fun toolBarSignUp() {
        this.binding.toolbarAuth.toolbar.title = "Sign Up"
        this.binding.toolbarAuth.toolbar.subtitle = "Register and eat"
        this.binding.toolbarAuth.toolbar.navigationIcon = resources.getDrawable(R.drawable.arrow_back_ios_24px, null)
        this.binding.toolbarAuth.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    fun toolBarSignUpAddress() {
        this.binding.toolbarAuth.toolbar.title = "Address"
        this.binding.toolbarAuth.toolbar.subtitle = "Make sure it's valid"
        this.binding.toolbarAuth.toolbar.navigationIcon = resources.getDrawable(R.drawable.arrow_back_ios_24px, null)
        this.binding.toolbarAuth.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    fun toolbarSignUpSuccess() {
        this.binding.toolbarAuth.toolbar.visibility = View.GONE
    }
}