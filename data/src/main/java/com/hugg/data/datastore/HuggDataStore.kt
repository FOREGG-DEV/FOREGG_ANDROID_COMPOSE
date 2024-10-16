package com.hugg.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("hugg_data_store")

class HuggDataStore(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        val CH_NICKNAME_KEY = stringPreferencesKey("challenge_nickname")
    }

    val challengeNickname: Flow<String?> = dataStore.data.map { preferences ->
        preferences[CH_NICKNAME_KEY]
    }

    suspend fun saveNickname(nickname: String) {
        dataStore.edit { preferences ->
            preferences[CH_NICKNAME_KEY] = nickname
        }
    }
}