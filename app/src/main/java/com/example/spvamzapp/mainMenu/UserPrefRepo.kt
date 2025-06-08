package com.example.spvamzapp.mainMenu

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPrefRepo(
    private val dataStore: DataStore<Preferences>
) {
    val chosenTheme: Flow<Int> = dataStore.data
        .catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { prefs ->
        prefs[THEME] ?: 0
    }

    private companion object {
        val THEME = intPreferencesKey("theme")
    }

    suspend fun saveThemePreference(themeChoice: Int) {
        dataStore.edit { preferences ->
            preferences[THEME] = themeChoice
        }
    }
}