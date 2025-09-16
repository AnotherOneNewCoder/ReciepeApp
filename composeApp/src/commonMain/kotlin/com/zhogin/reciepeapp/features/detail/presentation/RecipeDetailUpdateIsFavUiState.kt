package com.zhogin.reciepeapp.features.detail.presentation

data class RecipeDetailUpdateIsFavUiState(
    val isSuccess: Boolean? = null,
    val isUpdating: Boolean = true,
    val error: String? = null,
)