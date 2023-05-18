package com.example.githubapi.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingTheme (private val dataStore: DataStore<Preferences>) {
    private val THEME_KEY = booleanPreferencesKey("THEME SETTING")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences -> preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences -> preferences[THEME_KEY] = isDarkModeActive
        }
    }
    companion object {
        @Volatile
        private var INSTANCE: SettingTheme? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingTheme {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingTheme(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}