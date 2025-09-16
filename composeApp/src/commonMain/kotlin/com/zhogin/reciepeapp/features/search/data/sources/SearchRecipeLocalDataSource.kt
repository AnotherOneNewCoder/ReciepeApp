package com.zhogin.reciepeapp.features.search.data.sources

import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem

interface SearchRecipeLocalDataSource {
    suspend fun searchRecipeByText(query: String): List<RecipeItem>
}