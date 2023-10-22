package com.jyotish.template.network.model

import com.google.gson.annotations.SerializedName

data class BasicResponse (
    @SerializedName("status")
    val password: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("token")
    val token: String
)