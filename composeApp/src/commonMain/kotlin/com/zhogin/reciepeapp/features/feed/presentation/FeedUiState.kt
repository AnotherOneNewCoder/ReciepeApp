package com.zhogin.reciepeapp.features.feed.presentation

import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem

data class FeedUiState(
    val recipes: List<RecipeItem>? = null,
    val recipeLoading: Boolean = true,
    val recipeError: String? = null,
)