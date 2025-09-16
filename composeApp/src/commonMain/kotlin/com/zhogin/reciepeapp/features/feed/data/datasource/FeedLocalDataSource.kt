package com.zhogin.reciepeapp.features.feed.data.datasource

import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem

interface FeedLocalDataSource {
    suspend fun getRecipeList(): List<RecipeItem>
    suspend fun saveRecipeList(recipes: List<RecipeItem>)
}