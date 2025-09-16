package com.zhogin.reciepeapp.features.feed.data.repository

import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem
import com.zhogin.reciepeapp.features.feed.data.datasource.FeedLocalDataSource
import com.zhogin.reciepeapp.features.feed.data.datasource.FeedRemoteDataSource
import com.zhogin.reciepeapp.features.feed.domain.repository.FeedRepository

class FeedRepositoryImpl(
    private val feedLocalDataSource: FeedLocalDataSource,
    private val feedRemoteDataSource: FeedRemoteDataSource
): FeedRepository {
    override suspend fun getRecipeList(): Result<List<RecipeItem>> {
        return try {
            val recipeListCache = feedLocalDataSource.getRecipeList()
            val count = recipeListCache.count()
            return if (count > 0) {
                Result.success(recipeListCache)
            } else {
                val recipeListRemote = feedRemoteDataSource.getRecipeList()
                feedLocalDataSource.saveRecipeList(recipeListRemote)
                Result.success(recipeListRemote)
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}