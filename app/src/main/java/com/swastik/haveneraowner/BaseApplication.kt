package com.swastik.haveneraowner

import android.app.Application
import com.swastik.haveneraowner.data.di.appModule
import com.swastik.haveneraowner.data.di.useCaseModule
import com.swastik.haveneraowner.data.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin {
            androidContext(this@BaseApplication)
            modules(appModule, useCaseModule, viewModelModule)
        }
    }
}
