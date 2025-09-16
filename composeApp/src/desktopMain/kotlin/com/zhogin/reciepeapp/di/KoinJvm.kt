package com.zhogin.reciepeapp.di

import com.zhogin.reciepeapp.dbFactory.DatabaseFactory
import com.zhogin.reciepeapp.preferences.MultiplatformSettingsFactory
import org.koin.dsl.module

val jvmModules = module {
    single { DatabaseFactory() }
    single { MultiplatformSettingsFactory() }
}

fun initKoinJvm() = initKoin(
    additionalModules = listOf(jvmModules)
)