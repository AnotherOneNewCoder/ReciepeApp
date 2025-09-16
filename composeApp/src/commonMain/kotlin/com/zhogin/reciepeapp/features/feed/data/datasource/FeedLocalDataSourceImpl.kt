package com.zhogin.reciepeapp.features.feed.data.datasource

import com.zhogin.reciepeapp.features.common.data.database.daos.RecipeDao
import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem

class FeedLocalDataSourceImpl(
    private val recipeDao: RecipeDao
) : FeedLocalDataSource {
    override suspend fun getRecipeList(): List<RecipeItem> {
        return recipeDao.getAllRecipes()
    }

    override suspend fun saveRecipeList(recipes: List<RecipeItem>) = recipeDao.insertRecipeBulk(recipes)

}