package com.zhogin.reciepeapp.di

import com.zhogin.reciepeapp.features.detail.presentation.RecipeDetailViewModel
import com.zhogin.reciepeapp.features.favorites.presentation.FavoriteRecipeViewModel
import org.koin.core.module.dsl.viewModel
import com.zhogin.reciepeapp.features.feed.presentation.FeedViewModel
import com.zhogin.reciepeapp.features.login.presentation.LoginViewModel
import com.zhogin.reciepeapp.features.profile.presentation.ProfileViewModel
import com.zhogin.reciepeapp.features.search.presentation.SearchRecipeViewModel
import org.koin.dsl.module

fun viewModelModule() = module {
    viewModel {
        FeedViewModel(get())
    }
    viewModel {
        RecipeDetailViewModel(get())
    }
    viewModel {
        FavoriteRecipeViewModel(get())
    }
    viewModel {
        ProfileViewModel()
    }
    viewModel {
        LoginViewModel()
    }
    viewModel {
        SearchRecipeViewModel(get())
    }
}