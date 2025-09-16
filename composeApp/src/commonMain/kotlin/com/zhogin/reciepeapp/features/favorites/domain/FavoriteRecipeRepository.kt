package com.zhogin.reciepeapp.features.favorites.domain

import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem

interface FavoriteRecipeRepository {
    suspend fun getAllFavoriteRecipes(): Result<List<RecipeItem>>
    suspend fun addFavorite(recipeId: Long)
    suspend fun removeFavorite(recipeId: Long)
}