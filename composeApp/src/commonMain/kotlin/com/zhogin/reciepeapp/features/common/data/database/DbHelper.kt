package com.zhogin.reciepeapp.features.common.data.database

import com.zhogin.reciepeapp.RecipeAppKMPAppDb
import com.zhogin.reciepeapp.dbFactory.DatabaseFactory
import comzhoginreciepeapp.Recipe
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class DbHelper(
    private val dbFactory: DatabaseFactory
) {
    private var db: RecipeAppKMPAppDb? = null
    private val mutex = Mutex()

    suspend fun <Result : Any?> withDatabase(block: suspend (RecipeAppKMPAppDb) -> Result) =
        mutex.withLock {
            if (db == null) {
                db = createDb(dbFactory)
            }

            return@withLock block(db!!)
        }

    private suspend fun createDb(driverFactory: DatabaseFactory): RecipeAppKMPAppDb {
        return RecipeAppKMPAppDb(
            driver = driverFactory.createDriver(),
            RecipeAdapter = Recipe.Adapter(
                ingredientsAdapter = listOfStringsAdapter,
                instructionsAdapter = listOfStringsAdapter,
            )
        )
    }

}