package com.zhogin.reciepeapp.di

import com.zhogin.reciepeapp.features.detail.data.datasources.RecipeDetailLocalDataSource
import com.zhogin.reciepeapp.features.detail.data.datasources.RecipeDetailLocalDataSourceImpl
import com.zhogin.reciepeapp.features.detail.data.datasources.RecipeDetailRemoteDataSource
import com.zhogin.reciepeapp.features.detail.data.datasources.RecipeDetailRemoteDataSourceImpl
import com.zhogin.reciepeapp.features.detail.data.repositories.RecipeDetailRepositoryImpl
import com.zhogin.reciepeapp.features.detail.domain.repository.RecipeDetailRepository
import com.zhogin.reciepeapp.features.favorites.data.repository.FavoriteRecipeRepositoryImpl
import com.zhogin.reciepeapp.features.favorites.data.source.FavoriteRecipeLocalDataSource
import com.zhogin.reciepeapp.features.favorites.data.source.FavoriteRecipeLocalDataSourceImpl
import com.zhogin.reciepeapp.features.favorites.domain.FavoriteRecipeRepository
import com.zhogin.reciepeapp.features.feed.data.datasource.FeedLocalDataSource
import com.zhogin.reciepeapp.features.feed.data.datasource.FeedLocalDataSourceImpl
import com.zhogin.reciepeapp.features.feed.data.datasource.FeedRemoteDataSource
import com.zhogin.reciepeapp.features.feed.data.datasource.FeedRemoteDataSourceImpl
import com.zhogin.reciepeapp.features.feed.data.repository.FeedRepositoryImpl
import com.zhogin.reciepeapp.features.feed.domain.repository.FeedRepository
import com.zhogin.reciepeapp.features.search.data.repository.SearchRecipeRepositoryImpl
import com.zhogin.reciepeapp.features.search.data.sources.SearchRecipeLocalDataSource
import com.zhogin.reciepeapp.features.search.data.sources.SearchRecipeLocalDataSourceImpl
import com.zhogin.reciepeapp.features.search.domain.SearchRecipeRepository
import com.zhogin.reciepeapp.preferences.AppPreferences
import com.zhogin.reciepeapp.preferences.AppPreferencesImpl
import org.koin.dsl.module

fun dataModule() = module {
    single<AppPreferences> { AppPreferencesImpl(get()) }


    single<FeedLocalDataSource> { FeedLocalDataSourceImpl(get()) }
    single<FeedRemoteDataSource> { FeedRemoteDataSourceImpl(get()) }

    single<RecipeDetailLocalDataSource> { RecipeDetailLocalDataSourceImpl(get(), get()) }
    single<RecipeDetailRemoteDataSource> { RecipeDetailRemoteDataSourceImpl(get()) }

    single<SearchRecipeLocalDataSource> { SearchRecipeLocalDataSourceImpl(get()) }
    single<SearchRecipeRepository> { SearchRecipeRepositoryImpl(get()) }


    single<FavoriteRecipeLocalDataSource> { FavoriteRecipeLocalDataSourceImpl(get()) }

    single<FeedRepository> { FeedRepositoryImpl(get(), get()) }
    single<RecipeDetailRepository> { RecipeDetailRepositoryImpl(get(), get()) }

    single<FavoriteRecipeRepository> { FavoriteRecipeRepositoryImpl(get()) }
}