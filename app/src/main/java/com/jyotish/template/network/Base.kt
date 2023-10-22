package com.jyotish.template.network

object Base {
    @JvmStatic
    val baseUrl: String
        external get
    @JvmStatic
    val newBaseUrl: String
        external get
    @JvmStatic
    val testUrl: String
        external get

    init {
        System.loadLibrary("native-lib")
    }
}