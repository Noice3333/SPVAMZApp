package com.example.spvamzapp.mainMenu

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

//constants should be constant values
private const val LAYOUT_PREFERENCE_NAME = "layout_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = LAYOUT_PREFERENCE_NAME
)

class MainApplication : Application() {
    lateinit var userPrefRepo: UserPrefRepo

    override fun onCreate() {
        super.onCreate()
        userPrefRepo = UserPrefRepo(dataStore)
    }
}