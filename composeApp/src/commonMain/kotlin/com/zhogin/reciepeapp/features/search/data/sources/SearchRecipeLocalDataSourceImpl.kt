package com.zhogin.reciepeapp.features.search.data.sources

import com.zhogin.reciepeapp.features.common.data.database.daos.RecipeDao
import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem

class SearchRecipeLocalDataSourceImpl(
    private val recipeDao: RecipeDao
) : SearchRecipeLocalDataSource {
    override suspend fun searchRecipeByText(query: String): List<RecipeItem> {
        return recipeDao.searchRecipes(query)
    }
}