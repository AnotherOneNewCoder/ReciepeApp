package com.zhogin.reciepeapp.features.detail.data.repositories

import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem
import com.zhogin.reciepeapp.features.detail.data.datasources.RecipeDetailLocalDataSource
import com.zhogin.reciepeapp.features.detail.data.datasources.RecipeDetailRemoteDataSource
import com.zhogin.reciepeapp.features.detail.domain.repository.RecipeDetailRepository

class RecipeDetailRepositoryImpl(
    private val recipeDetailLocalDataSource: RecipeDetailLocalDataSource,
    private val recipeDetailRemoteDataSource: RecipeDetailRemoteDataSource,
): RecipeDetailRepository {
    override suspend fun getRecipeDetail(id: Long): Result<RecipeItem> {
        return try {
            val recipeLocal = recipeDetailLocalDataSource.getRecipeDetail(id)
            if (recipeLocal != null) {
                val isFav = recipeDetailLocalDataSource.isFavorite(id)
                return Result.success(recipeLocal.copy(
                    isFavorite = isFav
                ))
            } else {
                val recipeRemote = recipeDetailRemoteDataSource.getRecipeDetail(id)
                if (recipeRemote == null) {
                    return Result.failure(Exception("Recipe not found!"))
                } else {
                    recipeDetailLocalDataSource.saveRecipeDetail(recipeRemote)
                    return Result.success(recipeRemote)
                }

            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addToFavorite(recipeId: Long) {
        recipeDetailLocalDataSource.addToFavorite(recipeId)
    }

    override suspend fun removeFromFavorite(recipeId: Long) {
        recipeDetailLocalDataSource.removeFromFavorite(recipeId)
    }
}