package com.zhogin.reciepeapp.features.detail.data.datasources

import com.zhogin.reciepeapp.features.common.data.api.BASEURL
import com.zhogin.reciepeapp.features.common.data.model.RecipeResponseDto
import com.zhogin.reciepeapp.features.common.data.model.toRecipeItem
import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RecipeDetailRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : RecipeDetailRemoteDataSource {
    override suspend fun getRecipeDetail(id: Long): RecipeItem? {
        val httpResponse = httpClient.get("${BASEURL}lookup.php?i=$id")
        return httpResponse.body<RecipeResponseDto>().meals.firstOrNull()?.toRecipeItem()
    }
}