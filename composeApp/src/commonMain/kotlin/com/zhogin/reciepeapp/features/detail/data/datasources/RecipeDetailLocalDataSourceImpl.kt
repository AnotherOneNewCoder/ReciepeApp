package com.zhogin.reciepeapp.features.detail.data.datasources

import com.zhogin.reciepeapp.features.common.data.database.daos.FavoriteRecipeDao
import com.zhogin.reciepeapp.features.common.data.database.daos.RecipeDao
import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem

class RecipeDetailLocalDataSourceImpl(
    private val recipeDao: RecipeDao,
    private val favoriteRecipeDao: FavoriteRecipeDao,
) : RecipeDetailLocalDataSource {
    override suspend fun getRecipeDetail(id: Long): RecipeItem? {
        return recipeDao.getRecipeById(id)
    }

    override suspend fun saveRecipeDetail(recipeItem: RecipeItem) {
        recipeDao.insertRecipe(recipeItem)
    }

    override suspend fun addToFavorite(recipeId: Long) {
        favoriteRecipeDao.addFavorite(recipeId)
    }

    override suspend fun removeFromFavorite(recipeId: Long) {
        favoriteRecipeDao.removeFavorite(recipeId)
    }

    override suspend fun isFavorite(recipeId: Long): Boolean =
       favoriteRecipeDao.isFavorite(recipeId)

}