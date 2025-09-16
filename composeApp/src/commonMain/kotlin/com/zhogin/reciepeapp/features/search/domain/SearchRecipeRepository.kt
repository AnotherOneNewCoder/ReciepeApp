package com.zhogin.reciepeapp.features.search.domain

import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem

interface SearchRecipeRepository {
    suspend fun searchRecipeByText(text: String): Result<List<RecipeItem>>
}