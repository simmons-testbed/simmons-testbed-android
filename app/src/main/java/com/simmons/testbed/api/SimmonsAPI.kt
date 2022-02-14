package com.simmons.testbed.api

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface SimmonsAPI {

    @Headers("Content-Type: application/json")
    @POST("setBound")
    suspend fun setBound(
        @Body body: JsonObject
    ): Response<Int>

    @Headers("Content-Type: application/json")
    @POST("storeNum")
    suspend fun setStoreNum(
        @Body body: JsonObject
    ): Response<Int>

    @Headers("Content-Type: application/json")
    @GET("check")
    suspend fun getCheckStatus(): Response<Int>
}
