package com.zhogin.reciepeapp.features.search.data.repository

import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem
import com.zhogin.reciepeapp.features.search.data.sources.SearchRecipeLocalDataSource
import com.zhogin.reciepeapp.features.search.domain.SearchRecipeRepository

class SearchRecipeRepositoryImpl(
    private val searchRecipeLocalDataSource: SearchRecipeLocalDataSource
) : SearchRecipeRepository {
    override suspend fun searchRecipeByText(text: String): Result<List<RecipeItem>> {
        return try {
            val localResponse = searchRecipeLocalDataSource.searchRecipeByText(text)
            Result.success(localResponse)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}