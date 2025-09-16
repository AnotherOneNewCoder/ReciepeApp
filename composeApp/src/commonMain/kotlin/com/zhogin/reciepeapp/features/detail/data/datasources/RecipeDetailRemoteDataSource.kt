package com.zhogin.reciepeapp.features.detail.data.datasources

import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem

interface RecipeDetailRemoteDataSource {
    suspend fun getRecipeDetail(id: Long): RecipeItem?
}