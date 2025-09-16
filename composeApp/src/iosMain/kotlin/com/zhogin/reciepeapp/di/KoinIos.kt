package com.zhogin.reciepeapp.di

import com.zhogin.reciepeapp.dbFactory.DatabaseFactory
import com.zhogin.reciepeapp.preferences.MultiplatformSettingsFactory
import org.koin.dsl.module

val iosModules = module {
    single { DatabaseFactory() }
    single { MultiplatformSettingsFactory() }
}

fun initKoinIos() = initKoin(
    additionalModules = listOf(iosModules)
)