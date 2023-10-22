package com.jyotish.template.network

import com.jyotish.template.network.model.BasicResponse
import com.jyotish.template.network.model.TestResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    //pending
    @FormUrlEncoded
    @POST("user/phone/verify")
    fun verifyPhoneCode(
        @Field("user_id") userId: String,
        @Field("token") token: String
    ): Call<BasicResponse>

    //pending
    @GET("instructions/howItWorks/store/{storeId}")
    fun getHIW(
        @Path("storeId") storeId: Int,
        @Query("lang") lang: String
    ): Call<BasicResponse>


    @FormUrlEncoded
    @PUT("rewards/vouchers/use")
    fun useVouchers(
        @Field("voucher_id") voucherId: Int,
        @Field("is_used") isUsed: Int
    ): Call<BasicResponse>


    @GET("v2/goubba-pay/processing-fee")
    suspend fun getProcessingFee(
        @Header("authorization") token: String,
        @Query("method_name") methodName: String,
    ): BasicResponse


    @FormUrlEncoded
    @POST("v2/card/payment")
    suspend fun buyCardsWithInternationalCard(
        @Header("authorization") token: String,
        @Field("order_id") orderId: Int,
        @Field("promo_code") promoCode: String?,
        @Field("payment_method") paymentMethod: String = "Debit / Credit card",
        @Field("number") number: String,
        @Field("exp_month") expMonth: String,
        @Field("exp_year") expYear: String,
        @Field("cvc") cvc: String
    ): BasicResponse

    @GET("v2/card/me")
    suspend fun getMyCardList(
        @Header("authorization") token: String
    ): BasicResponse

    @DELETE("v2/card/order/remove/{orderId}")
    suspend fun removeOrder(
        @Header("authorization") token: String,
        @Path("orderId") orderId: Int
    ): BasicResponse

    @GET("v2/card/order/details/{orderId}")
    suspend fun getOrderDetails(
        @Header("authorization") token: String,
        @Path("orderId") orderId:Int
    ): BasicResponse
    @GET("api/v2/live/stream")
    suspend fun getDemoApi(
        @Header("x-test-token") token: String,
        @Query("type") type:String
    ): TestResponse
    suspend fun getDemoApiWorker(
        @Header("x-test-token") token: String,
        @Query("type") type:String
    ): Call<TestResponse>
}



