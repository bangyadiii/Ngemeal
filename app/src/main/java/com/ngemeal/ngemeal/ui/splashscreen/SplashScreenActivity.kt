package com.ngemeal.ngemeal.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.ui.auth.AuthActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
           startActivity(Intent(this, AuthActivity::class.java))
            this.finish();
        }, 3000);

    }
}