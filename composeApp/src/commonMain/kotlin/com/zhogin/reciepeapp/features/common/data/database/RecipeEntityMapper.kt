package com.zhogin.reciepeapp.features.common.data.database

import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem
import comzhoginreciepeapp.Recipe

fun recipeEntityMapper(recipe: Recipe) = RecipeItem(
    id = recipe.id,
    title = recipe.title,
    description = recipe.description,
    category = recipe.category,
    area = recipe.area,
    imageUrl = recipe.imageUrl,
    youtubeLink = recipe.youtubeLink,
    ingredients = recipe.ingredients,
    instructions = recipe.instructions,
    isFavorite = recipe.isFavorite == 1L,
    rating = recipe.rating,
    duration = recipe.duration ?: "20 Mins",
    difficulty =recipe.difficulty ?: "Easy",
)