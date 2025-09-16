package com.zhogin.reciepeapp.features.favorites.presentation

import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem

data class FavoriteScreenUiState(
    val favoriteList: List<RecipeItem>? = null,
    val favoriteListIsLoading: Boolean = true,
    val favoriteError: String? = null,
)
