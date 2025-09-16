package com.zhogin.reciepeapp.di

import com.zhogin.reciepeapp.features.common.data.api.httpClient
import io.ktor.client.HttpClient
import org.koin.dsl.module

fun networkModule() = module {
    single<HttpClient> { httpClient }
}