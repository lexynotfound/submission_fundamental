package com.acer.mygithubapp

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

class PreferencesHelper(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "theme_preferences"
        private const val THEME_KEY = "theme_key"
        const val LIGHT_MODE = "light"
        const val DARK_MODE = "dark"
    }

    fun setThemeMode(theme: String) {
        preferences.edit().putString(THEME_KEY, theme).apply()
    }

    fun getThemeMode(): String {
        return preferences.getString(THEME_KEY, LIGHT_MODE) ?: LIGHT_MODE
    }

    fun applyTheme() {
        when (getThemeMode()) {
            LIGHT_MODE -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            DARK_MODE -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}