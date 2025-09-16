package com.zhogin.reciepeapp.dbFactory

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.zhogin.reciepeapp.RecipeAppKMPAppDb

actual class DatabaseFactory(
    private val context: Context
) {
    actual suspend fun createDriver(): SqlDriver {
        val scheme = RecipeAppKMPAppDb.Schema
        return AndroidSqliteDriver(
            schema = scheme.synchronous(),
            context = context,
            name = DB_FILE_NAME,
            callback = object : AndroidSqliteDriver.Callback(schema = scheme.synchronous()) {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    db.setForeignKeyConstraintsEnabled(true)
                }
            }
        )
    }
}