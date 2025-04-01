package com.example.marcusfitnesstracker

import android.app.Application
import com.example.marcusfitnesstracker.di.appModule
import org.koin.core.context.startKoin

class FitnessTracker : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
    }
}