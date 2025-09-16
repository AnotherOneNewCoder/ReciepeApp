package com.zhogin.reciepeapp.features.feed.data.datasource

import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem

interface FeedRemoteDataSource {
    suspend fun getRecipeList(): List<RecipeItem>
}