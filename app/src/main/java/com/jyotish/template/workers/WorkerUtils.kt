package com.jyotish.template.workers

import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.jyotish.template.application.AppController

object WorkerUtils {
    fun refreshUserInfo() {
        val workRequest = OneTimeWorkRequestBuilder<UserInfoRefreshWorker>()
            .build()
        WorkManager.getInstance(AppController.instance)
            .enqueueUniqueWork("user-info-refresh", ExistingWorkPolicy.REPLACE, workRequest)
    }
}