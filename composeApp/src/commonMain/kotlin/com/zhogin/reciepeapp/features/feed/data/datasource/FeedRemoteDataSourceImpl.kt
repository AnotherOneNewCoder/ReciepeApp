package com.zhogin.reciepeapp.features.feed.data.datasource

import com.zhogin.reciepeapp.features.common.data.api.BASEURL
import com.zhogin.reciepeapp.features.common.data.model.RecipeResponseDto
import com.zhogin.reciepeapp.features.common.data.model.toRecipeItem
import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class FeedRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : FeedRemoteDataSource {
    override suspend fun getRecipeList(): List<RecipeItem> {
        val httpResponse = httpClient.get("${BASEURL}search.php?f=b")
        val recipeListApiResponse = httpResponse.body<RecipeResponseDto>()
        return recipeListApiResponse.meals.mapNotNull {
            it.toRecipeItem()
        }
    }
}