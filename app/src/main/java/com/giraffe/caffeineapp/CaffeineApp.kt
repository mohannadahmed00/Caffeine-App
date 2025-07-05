package com.giraffe.caffeineapp

import android.app.Application
import com.giraffe.caffeineapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class CaffeineApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CaffeineApp)
            modules(appModule)
        }
    }
}