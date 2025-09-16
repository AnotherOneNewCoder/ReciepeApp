package com.zhogin.reciepeapp

import android.app.Application
import com.zhogin.reciepeapp.dbFactory.DatabaseFactory
import com.zhogin.reciepeapp.di.initKoin
import com.zhogin.reciepeapp.preferences.MultiplatformSettingsFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class RecipeApp: Application() {

    private val androidModules = module {
        single { DatabaseFactory(applicationContext) }
        single { MultiplatformSettingsFactory(applicationContext) }
    }

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        initKoin(
            additionalModules = listOf(androidModules),
        ) {
            androidContext(applicationContext)
        }
    }
}
