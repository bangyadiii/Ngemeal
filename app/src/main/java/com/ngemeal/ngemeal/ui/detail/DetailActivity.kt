package com.ngemeal.ngemeal.ui.detail

import android.os.Build
import android.os.Build.VERSION
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.databinding.ActivityAuthBinding
import com.ngemeal.ngemeal.databinding.ActivityDetailBinding
import com.ngemeal.ngemeal.model.response.home.Data
import com.ngemeal.ngemeal.ui.auth.signin.SignInContract

class DetailActivity : AppCompatActivity() {

    private var _binding : ActivityDetailBinding? = null
    private val binding get() = _binding!!
//    private var data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this._binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            val navController = Navigation.findNavController(findViewById(R.id.detailHostFragment))
            val bundle  = Bundle()
            if (VERSION.SDK_INT >= 33) {
                var data = it.getParcelable("data", Data::class.java)
                bundle.putParcelable("data", data )
            } else {
                var data = it.getParcelable<Data>("data")
                bundle.putParcelable("data", data )
            }
            navController.setGraph(R.navigation.nav_detail, bundle)
//            navController.navigate(R.id.nav_detail, bundle)
        }
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