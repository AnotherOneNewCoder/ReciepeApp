package com.zhogin.reciepeapp.features.common.data.database.daos

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import com.zhogin.reciepeapp.features.common.data.database.DbHelper
import com.zhogin.reciepeapp.features.common.data.database.recipeEntityMapper
import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class FavoriteRecipeDao(
    private val dbHelper: DbHelper
){
    suspend fun addFavorite(recipeId: Long) {
        val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        dbHelper.withDatabase { database ->
            database.favoriteRecipeQueries.upsertFavorite(
                recipe_id = recipeId,
                added_at = currentDateTime.toString()
            )
        }
    }

    suspend fun removeFavorite(recipeId: Long) {
        dbHelper.withDatabase { database ->
            database.favoriteRecipeQueries.deleteFavoriteRecipeById(
                recipe_id = recipeId,
            )
        }
    }

    suspend fun getAllFavoriteRecipes(): List<RecipeItem> {
        return dbHelper.withDatabase { database ->
            database.favoriteRecipeQueries.selectAllFavoriteRecipes().awaitAsList().map {
                recipeEntityMapper(it)
            }
        }
    }


    suspend fun isFavorite(recipeId: Long): Boolean {
        return dbHelper.withDatabase { database ->
            database.favoriteRecipeQueries.selectFavoriteRecipeById(recipeId).awaitAsOneOrNull() != null
        }
    }


}
