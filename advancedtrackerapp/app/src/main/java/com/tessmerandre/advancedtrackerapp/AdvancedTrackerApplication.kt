package com.tessmerandre.advancedtrackerapp

import android.app.Application
import com.tessmerandre.advancedtrackerapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AdvancedTrackerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AdvancedTrackerApplication)
            modules(appModule)
        }
    }

}