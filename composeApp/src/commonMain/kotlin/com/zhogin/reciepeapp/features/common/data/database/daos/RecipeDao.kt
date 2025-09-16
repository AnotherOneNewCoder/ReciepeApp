package com.zhogin.reciepeapp.features.common.data.database.daos

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import com.zhogin.reciepeapp.features.common.data.database.DbHelper
import com.zhogin.reciepeapp.features.common.data.database.recipeEntityMapper
import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem

class RecipeDao(
    private val dbHelper: DbHelper
){
    suspend fun insertRecipe(recipeItem: RecipeItem) {
        dbHelper.withDatabase { database ->
            database.recipeEntityQueries.insertRecipe(
                id = recipeItem.id,
                title = recipeItem.title,
                description = recipeItem.description,
                category = recipeItem.category,
                area = recipeItem.area,
                imageUrl = recipeItem.imageUrl,
                youtubeLink = recipeItem.youtubeLink,
                ingredients = recipeItem.ingredients,
                instructions = recipeItem.instructions,
                isFavorite = if (recipeItem.isFavorite) 1 else 0,
                rating = recipeItem.rating,
                duration = recipeItem.duration,
                difficulty = recipeItem.difficulty
            )
        }
    }

    suspend fun updateRecipe(recipeItem: RecipeItem) {
        dbHelper.withDatabase { database ->
            database.recipeEntityQueries.updateRecipe(
                title = recipeItem.title,
                description = recipeItem.description,
                category = recipeItem.category,
                area = recipeItem.area,
                imageUrl = recipeItem.imageUrl,
                youtubeLink = recipeItem.youtubeLink,
                ingredients = recipeItem.ingredients,
                instructions = recipeItem.instructions,
                isFavorite = if (recipeItem.isFavorite) 1 else 0,
                rating = recipeItem.rating,
                duration = recipeItem.duration,
                difficulty = recipeItem.difficulty,
                id = recipeItem.id,
            )
        }
    }

    suspend fun insertRecipeBulk(recipeItems: List<RecipeItem>) {
        dbHelper.withDatabase { database ->
            recipeItems.forEach { recipeItem ->
                database.recipeEntityQueries.insertRecipe(
                    id = recipeItem.id,
                    title = recipeItem.title,
                    description = recipeItem.description,
                    category = recipeItem.category,
                    area = recipeItem.area,
                    imageUrl = recipeItem.imageUrl,
                    youtubeLink = recipeItem.youtubeLink,
                    ingredients = recipeItem.ingredients,
                    instructions = recipeItem.instructions,
                    isFavorite = if (recipeItem.isFavorite) 1 else 0,
                    rating = recipeItem.rating,
                    duration = recipeItem.duration,
                    difficulty = recipeItem.difficulty
                )
            }

        }
    }
    suspend fun upsertRecipeBulk(recipeItems: List<RecipeItem>) {
        dbHelper.withDatabase { database ->
            recipeItems.forEach { recipeItem ->
                database.recipeEntityQueries.upsertRecipe(

                    title = recipeItem.title,
                    description = recipeItem.description,
                    category = recipeItem.category,
                    area = recipeItem.area,
                    imageUrl = recipeItem.imageUrl,
                    youtubeLink = recipeItem.youtubeLink,
                    ingredients = recipeItem.ingredients,
                    instructions = recipeItem.instructions,
                    isFavorite = if (recipeItem.isFavorite) 1 else 0,
                    rating = recipeItem.rating,
                    duration = recipeItem.duration,
                    difficulty = recipeItem.difficulty,
                    id = recipeItem.id,
                )
            }

        }
    }
    suspend fun getAllRecipes(): List<RecipeItem> {
        return dbHelper.withDatabase { database ->
            database.recipeEntityQueries.selectAllRecipes().awaitAsList().map {
                recipeEntityMapper(it)
            }
        }
    }

    suspend fun getRecipeById(id: Long): RecipeItem? {
        return dbHelper.withDatabase { database ->
            database.recipeEntityQueries.selectRecipeById(id).awaitAsOneOrNull()?.let {
                recipeEntityMapper(it)
            }
        }
    }
    suspend fun deleteRecipeById(id: Long) {
        dbHelper.withDatabase { database ->
            database.recipeEntityQueries.deleteRecipeById(id)
        }
    }

    suspend fun searchRecipes(text: String): List<RecipeItem> {
        return dbHelper.withDatabase { database ->
            database.recipeEntityQueries.searchRecipeByText(text).awaitAsList().map {
                recipeEntityMapper(it)
            }
        }
    }

}
