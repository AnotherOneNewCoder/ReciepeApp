package com.zhogin.reciepeapp.di

import com.zhogin.reciepeapp.dbFactory.DatabaseFactory
import com.zhogin.reciepeapp.preferences.MultiplatformSettingsFactory
import org.koin.dsl.module

val jsModules = module {
    single { DatabaseFactory() }
    single { MultiplatformSettingsFactory() }
}

fun initKoinJs() = initKoin(
    additionalModules = listOf(jsModules)
)