package com.zhogin.reciepeapp.features.feed.domain.repository

import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem

interface FeedRepository {
    suspend fun getRecipeList(): Result<List<RecipeItem>>
}