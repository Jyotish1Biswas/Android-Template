package com.jyotish.template.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.jyotish.template.data_store.UserDataStore
import com.jyotish.template.helper.errorMessage
import com.jyotish.template.network.Base.getToken
import com.jyotish.template.network.RetrofitClient
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import retrofit2.await

class UserInfoRefreshWorker(context: Context, params: WorkerParameters): CoroutineWorker(context, params) {

    private val userDataStore by lazy { UserDataStore.getInstance() }

    override suspend fun doWork(): Result {
        try {

            val info = coroutineScope {
                async {
                    RetrofitClient.INSTANCE.API.getDemoApiWorker(getToken, "C").await()
                }
            }
            userDataStore.updateUserCoin(userDataStore.getUserAvailableCoin().first() + 50)
        } catch (e: Exception) {
            Log.e("UserInfoWorker", e.errorMessage)
        }
        return Result.success()
    }
}