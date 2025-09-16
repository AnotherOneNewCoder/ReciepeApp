package com.zhogin.reciepeapp.features.common.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeResponseDto(
    @SerialName("meals")
    val meals: List<RecipeDto>,
)
