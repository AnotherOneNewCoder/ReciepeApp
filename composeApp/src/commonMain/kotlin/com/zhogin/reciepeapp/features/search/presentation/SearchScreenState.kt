package com.zhogin.reciepeapp.features.search.presentation

import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem

data class SearchScreenState(
    val idle: Boolean = true,
    val success: Boolean = false,
    val error: String? = null,
    val results: List<RecipeItem> = emptyList()
)
