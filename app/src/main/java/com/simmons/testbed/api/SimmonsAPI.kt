package com.simmons.testbed.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SimmonsAPI {
    @POST("setBound")
    suspend fun setBound(
        @Query("xboundary") xBoundary: String,
        @Query("yboundary") yBoundary: String
    ): Response<Int>

    @POST("storeNum")
    suspend fun setStoreNum(
        @Query("nowcheck") nowCheck: String,
        @Query("howmany") howMany: String
    ): Response<Int>

    @GET("check")
    fun getCheckStatus(): Response<Int>
}
