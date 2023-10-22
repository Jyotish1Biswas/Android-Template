package com.jyotish.template.data_store

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jyotish.template.application.AppController
import kotlinx.coroutines.flow.map


private const val USER_DATA_STORE = "user-data-store-Template"
private const val PREF_KEY_USER_AVAILABLE_COIN = "user-available-coin-key"
private const val PREF_KEY_PAID_USER = "paid-user-key"
private const val PREF_KEY_FOLLOWED_INSTAGRAM = "followed-instagram"
class UserDataStore private constructor(private val context: Context) {


    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = USER_DATA_STORE)

    private val userAvailableCoinPrefKey = longPreferencesKey(name = PREF_KEY_USER_AVAILABLE_COIN)
    private val paidUserPrefKey = booleanPreferencesKey(name = PREF_KEY_PAID_USER)
    private val followedInstagramPrefKey = booleanPreferencesKey(name = PREF_KEY_FOLLOWED_INSTAGRAM)

    suspend fun updateUserCoin(value: Long) {
        context.datastore.edit {
            it[userAvailableCoinPrefKey]= value
        }
    }

    val userAvailableCoinFlow = context.datastore.data.map {
        it[userAvailableCoinPrefKey] ?: 0
    }

    suspend fun getUserAvailableCoin() = context.datastore.data.map {
        it[userAvailableCoinPrefKey] ?: 0
    }

    suspend fun updateIsPaidUser(value: Boolean) {
        context.datastore.edit {
            it[paidUserPrefKey]= value
        }
    }

    val isPaidUserFlow = context.datastore.data.map {
        it[paidUserPrefKey]?:false
    }

    suspend fun getIsPaidUser() = context.datastore.data.map {
        it[paidUserPrefKey] ?: false
    }
    suspend fun updateIsFollowedInstagram(value: Boolean) {
        context.datastore.edit {
            it[followedInstagramPrefKey]= value
        }
    }

    val isFollowedInstagramFlow = context.datastore.data.map {
        it[followedInstagramPrefKey]?:false
    }

    suspend fun getsFollowedInstagram() = context.datastore.data.map {
        it[followedInstagramPrefKey] ?: false
    }


    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: UserDataStore? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: UserDataStore(AppController.instance).also {
                instance = it
            }
        }
    }
}