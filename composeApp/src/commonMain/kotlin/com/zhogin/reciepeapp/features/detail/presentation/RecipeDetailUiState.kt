package com.zhogin.reciepeapp.features.detail.presentation

import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem

data class RecipeDetailUiState(
    val recipeItem: RecipeItem? = null,
    val detailIsLoading: Boolean = true,
    val detailError: String? = null,
)