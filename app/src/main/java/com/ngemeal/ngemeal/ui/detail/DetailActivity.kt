package com.ngemeal.ngemeal.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.databinding.ActivityAuthBinding
import com.ngemeal.ngemeal.databinding.ActivityDetailBinding
import com.ngemeal.ngemeal.ui.auth.signin.SignInContract

class DetailActivity : AppCompatActivity() {

    private var _binding : ActivityDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this._binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

    }

    fun toolbarPayment(){
        binding.includeToolbar.toolbar.title = "Pembayaran"
        binding.includeToolbar.toolbar.subtitle = "Self reward buat kamu"
        binding.includeToolbar.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_000,null)
        binding.includeToolbar.toolbar.setNavigationOnClickListener{onBackPressed()}
        binding.includeToolbar.toolbar.visibility = View.VISIBLE
    }

    fun toolbarDetail(){
        binding.includeToolbar.toolbar.visibility = View.GONE
    }
}