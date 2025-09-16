package com.zhogin.reciepeapp.preferences

import com.russhwolf.settings.Settings

class AppPreferencesImpl(
    private val settingsFactory: MultiplatformSettingsFactory
) : AppPreferences {

    private val settings: Settings by lazy {
        settingsFactory.getSettings()
    }

    override fun clear() {
        settings.clear()
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return settings.getInt(
            key = key,
            defaultValue = defaultValue
        )
    }

    override fun getIntOrNull(key: String): Int? {
        return settings.getIntOrNull(key)
    }

    override fun getString(key: String, defaultValue: String): String {
        return settings.getString(
            key = key,
            defaultValue = defaultValue
        )
    }

    override fun getStringOrNull(key: String): String? {
        return settings.getStringOrNull(key)
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return settings.getLong(
            key = key,
            defaultValue = defaultValue
        )
    }

    override fun getLongOrNull(key: String): Long? {
        return settings.getLongOrNull(key)
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return settings.getBoolean(
            key = key,
            defaultValue = defaultValue
        )
    }

    override fun getBooleanOrNull(key: String): Boolean? {
        return settings.getBooleanOrNull(key)
    }

    override fun putInt(key: String, values: Int) {
        settings.putInt(
            key = key,
            value = values
        )
    }

    override fun putString(key: String, values: String) {
        settings.putString(
            key = key,
            value = values
        )
    }

    override fun putLong(key: String, values: Long) {
        settings.putLong(
            key = key,
            value = values
        )
    }

    override fun putBoolean(key: String, values: Boolean) {
        settings.putBoolean(
            key = key,
            value = values
        )
    }

    override fun hasKey(key: String): Boolean {
        return settings.hasKey(key)
    }

    override fun remove(key: String) {
        settings.remove(key)
    }
}