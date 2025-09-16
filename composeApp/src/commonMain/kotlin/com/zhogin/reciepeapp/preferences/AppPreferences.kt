package com.zhogin.reciepeapp.preferences

interface AppPreferences {
    fun clear()
    fun getInt(key: String, defaultValue: Int): Int
    fun getIntOrNull(key: String): Int?
    fun getString(key: String, defaultValue: String): String
    fun getStringOrNull(key: String): String?
    fun getLong(key: String, defaultValue: Long): Long
    fun getLongOrNull(key: String): Long?
    fun getBoolean(key: String, defaultValue: Boolean): Boolean
    fun getBooleanOrNull(key: String): Boolean?

    fun putInt(key: String, values: Int)
    fun putString(key: String, values: String)
    fun putLong(key: String, values: Long)
    fun putBoolean(key: String, values: Boolean)

    fun hasKey(key: String): Boolean
    fun remove(key: String)
}