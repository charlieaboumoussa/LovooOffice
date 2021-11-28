package com.lovoo.lovoooffice.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.lovoo.lovoooffice.common.base.ui.BaseActivity
import com.lovoo.lovoooffice.common.base.viewmodels.BaseViewModel
import com.lovoo.lovoooffice.databinding.ActivitySplashBinding
import com.lovoo.lovoooffice.presentation.landing.LandingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private lateinit var _binding: ActivitySplashBinding
    private val _viewModel : SplashViewModel by viewModels()

    override fun attachViewModel(): BaseViewModel = _viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        observeUiActionState()
        _viewModel.getOfficeFilters()
    }

    fun observeUiActionState(){
        lifecycleScope.launch {
            _viewModel.uiActions.collect {
                when(it.first){
                    NavFlow.START_APP->{
                        lifecycleScope.launch {
                            withContext(Dispatchers.Main){
                                delay(1000)
                                startApp()
                            }
                        }
                    }
                    else->{}
                }
            }
        }
    }

    fun startApp(){
        val intent = Intent(this, LandingActivity::class.java)
        startActivity(intent)
        finish()
    }

}