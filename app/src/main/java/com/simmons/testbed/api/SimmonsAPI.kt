package com.simmons.testbed.api

import com.google.gson.JsonObject
import com.simmons.testbed.api.response.CryDetectResponse
import com.simmons.testbed.api.response.DetectResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

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

    @Headers("Content-Type: application/json")
    @POST("cryDetect")
    suspend fun detectCry(
        @Body body: JsonObject
    ): Response<CryDetectResponse>

    @Headers("Content-Type: application/json")
    @GET("getDetect")
    suspend fun getDetect(
        @Query("id") id: Int
    ): Response<DetectResponse>
}
