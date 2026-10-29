package br.com.furlaneto.murilo.shoplite.core.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import br.com.furlaneto.murilo.shoplite.enum.ThemeModeApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class AppPreferences(private val context: Context) {

    private val themeKey = stringPreferencesKey("theme_mode")

    fun themeFlow(): Flow<String> = context.dataStore.data.map { preferences ->
        preferences[themeKey] ?: ThemeModeApp.SYSTEM.value
    }

    suspend fun saveTheme(themeMode: ThemeModeApp) {
        context.dataStore.edit { preferences ->
            preferences[themeKey] = themeMode.value
        }
    }
}
