package com.zhogin.reciepeapp.dbFactory


import app.cash.sqldelight.db.SqlDriver

const val DB_FILE_NAME = "kmp.db"

expect class DatabaseFactory {
    suspend fun createDriver(): SqlDriver
}