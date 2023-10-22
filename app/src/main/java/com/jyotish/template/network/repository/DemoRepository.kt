package com.jyotish.template.network.repository

import com.jyotish.template.network.RetrofitClient
import com.jyotish.template.network.model.BasicResponse

class DemoRepository {
    private val apiClient = RetrofitClient.INSTANCE.API
    private val clientWithErrorInterceptor = RetrofitClient.INSTANCE.API_NEW

    suspend fun removeOrder(orderId: Int): BasicResponse {
        return apiClient.removeOrder("Token", orderId)
    }

    suspend fun getOrderDetails(orderId: Int) =
        apiClient.getOrderDetails("Token", orderId)
  suspend fun getDemoApi(type: String) =
        apiClient.getDemoApi("1561905fe0fc8d5c642187dcdbbaaff170ac126d", type)

    companion object {

        @Volatile
        private var instance: DemoRepository? = null
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: DemoRepository().also {
                instance = it
            }
        }
    }
}