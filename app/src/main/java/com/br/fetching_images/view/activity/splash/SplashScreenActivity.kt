package com.br.fetching_images.view.activity.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.br.fetching_images.R
import com.br.fetching_images.network.constants.APIConstants
import com.br.fetching_images.util.getApiToken
import com.br.fetching_images.util.setApiToken
import com.br.fetching_images.view.activity.search.ResultSearchActivity

class SplashScreenActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    override fun onResume() {
        super.onResume()
        next()
    }

    private fun next(){
        Handler().postDelayed({
            if (getApiToken() == null) {
                setApiToken(APIConstants.CLIENT_ID)
            }
            startActivity(Intent(this, ResultSearchActivity::class.java))
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in)
            finish()

        }, 1500)
    }

    override fun onBackPressed(){}
}