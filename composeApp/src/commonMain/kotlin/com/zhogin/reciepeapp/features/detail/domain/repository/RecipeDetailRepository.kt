package com.zhogin.reciepeapp.features.detail.domain.repository

import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem

interface RecipeDetailRepository {
    suspend fun getRecipeDetail(id: Long): Result<RecipeItem>
    suspend fun addToFavorite(recipeId: Long)
    suspend fun removeFromFavorite(recipeId: Long)
}