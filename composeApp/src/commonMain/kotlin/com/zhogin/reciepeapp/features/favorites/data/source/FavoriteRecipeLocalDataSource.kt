package com.zhogin.reciepeapp.features.favorites.data.source

import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem

interface FavoriteRecipeLocalDataSource {
    suspend fun getAllFavoriteRecipes(): List<RecipeItem>
    suspend fun addFavorite(recipeId: Long)
    suspend fun removeFavorite(recipeId: Long)
}