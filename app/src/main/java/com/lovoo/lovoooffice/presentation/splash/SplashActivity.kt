package com.lovoo.lovoooffice.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.lovoo.lovoooffice.common.base.ui.BaseActivity
import com.lovoo.lovoooffice.databinding.ActivitySplashBinding
import com.lovoo.lovoooffice.presentation.landing.LandingActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashActivity : BaseActivity() {

    private lateinit var _binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        
        lifecycleScope.launch {
            withContext(Dispatchers.Main){
                delay(3000)
                startApp()
            }
        }
    }

    fun startApp(){
        val intent = Intent(this, LandingActivity::class.java)
        startActivity(intent)
        finish()
    }

}