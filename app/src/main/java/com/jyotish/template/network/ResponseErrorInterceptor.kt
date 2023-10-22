package com.jyotish.template.network

import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class ResponseErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val response = chain.proceed(chain.request())

        val responseJson = runCatching { JSONObject(response.peekBody(5000).string()) }.getOrNull()
        return if (responseJson?.getInt("status") == 0) {
            val errorMessage = runCatching {
                responseJson.getJSONArray("msg").getString(0)
            }.getOrElse {
                runCatching {
                    responseJson.getJSONArray("errors").getString(0)
                }.getOrNull()
            }
            if (errorMessage != null) {
                throw IOException(errorMessage)
            } else {
                response
            }
        } else {
            response
        }
    }

}