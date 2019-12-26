package com.felix.nasa

import android.app.Application
import com.felix.nasa.data.modules.baseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                baseModule
            )
        }
    }
}