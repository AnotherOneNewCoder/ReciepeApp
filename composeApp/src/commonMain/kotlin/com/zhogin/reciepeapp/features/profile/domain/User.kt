package com.zhogin.reciepeapp.features.profile.domain

data class User(
    val id: Long,
    val name: String,
    val email: String,
    val myRecipeCount: Int,
    val favoriteRecipeCount: Int,
    val followers: Int,
)