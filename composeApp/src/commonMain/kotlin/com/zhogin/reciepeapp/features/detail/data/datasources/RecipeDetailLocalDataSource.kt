package com.zhogin.reciepeapp.features.detail.data.datasources

import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem

interface RecipeDetailLocalDataSource {
    suspend fun getRecipeDetail(id: Long): RecipeItem?
    suspend fun saveRecipeDetail(recipeItem: RecipeItem)
    suspend fun addToFavorite(recipeId: Long)
    suspend fun removeFromFavorite(recipeId: Long)
    suspend fun isFavorite(recipeId: Long): Boolean
}