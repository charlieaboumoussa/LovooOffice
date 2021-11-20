package com.lovoo.lovoooffice

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import com.lovoo.lovoooffice.core.data.ServiceLocator

class App : Application(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()
        ServiceLocator.initializeDatabase(this)
        ServiceLocator.initializeRetrofit(this)
    }
}