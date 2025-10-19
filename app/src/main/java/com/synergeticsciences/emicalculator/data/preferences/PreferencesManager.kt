package com.synergeticsciences.emicalculator.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.synergeticsciences.emicalculator.data.model.SupportedCurrencies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferencesManager(private val context: Context) {
    
    companion object {
        private val CURRENCY_CODE = stringPreferencesKey("currency_code")
    }
    
    val selectedCurrencyCode: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[CURRENCY_CODE] ?: SupportedCurrencies.INR.code
        }
    
    suspend fun setCurrency(currencyCode: String) {
        context.dataStore.edit { preferences ->
            preferences[CURRENCY_CODE] = currencyCode
        }
    }
}

