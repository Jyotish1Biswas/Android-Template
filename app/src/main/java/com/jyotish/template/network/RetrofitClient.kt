package com.jyotish.template.network

import android.util.Log
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.GsonBuilder
import com.jyotish.template.BuildConfig
import com.jyotish.template.application.AppController
import com.jyotish.template.utils.GsonExclusionStrategy
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient private constructor(){

    private val gson by lazy {
        GsonBuilder().apply {
            setExclusionStrategies(GsonExclusionStrategy())
        }.create()
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Base.newBaseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient())
        .build()

    val API: ApiInterface by lazy { retrofit.create(ApiInterface::class.java) }

    val API_NEW: ApiInterface by lazy {
        retrofit.newBuilder()
            .client(clientWithErrorInterceptor())
            .build()
            .create(ApiInterface::class.java)
    }

    companion object {

        private val loggingInterceptor = HttpLoggingInterceptor { log ->
            Log.e("REST-API", log)
        }.apply { level = HttpLoggingInterceptor.Level.BASIC }

        private var mInstance: RetrofitClient? = null

        @get:Synchronized
        val INSTANCE: RetrofitClient
            get() {
                if (mInstance == null) mInstance = RetrofitClient()
                return mInstance!!
            }

        private fun okHttpClient(): OkHttpClient {
            return OkHttpClient.Builder().apply {
                readTimeout(30, TimeUnit.SECONDS)
                writeTimeout(15, TimeUnit.SECONDS)
                connectTimeout(1, TimeUnit.MINUTES)
                if (BuildConfig.DEBUG) {
                    //addInterceptor(loggingInterceptor)
                    addInterceptor(
                        ChuckerInterceptor.Builder(AppController.instance)
                            .collector(ChuckerCollector(AppController.instance))
                            .maxContentLength(250000L)
                            .redactHeaders(emptySet())
                            .alwaysReadResponseBody(true)
                            .build()
                    )
                }
            }.build()
        }

        private fun clientWithErrorInterceptor(): OkHttpClient {
            return okHttpClient().newBuilder().apply {
                addInterceptor(ResponseErrorInterceptor())
            }.build()
        }
    }

}