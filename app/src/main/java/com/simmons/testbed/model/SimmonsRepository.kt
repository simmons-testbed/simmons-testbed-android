package com.simmons.testbed.model

import com.google.gson.JsonObject
import com.simmons.testbed.api.SimmonsAPI
import javax.inject.Inject

class SimmonsRepository @Inject constructor(private val simmonsAPI: SimmonsAPI) {

    suspend fun setBound(xBoundary: String, yBoundary: String): Int? {
        return try {
            val jsonObject = JsonObject().apply {
                addProperty("xboundary", xBoundary)
                addProperty("yboundary", yBoundary)
            }

            simmonsAPI.setBound(jsonObject).body()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun setStoreNum(nowCheck: String, howMany: String): Int? {
        return try {
            val jsonObject = JsonObject().apply {
                addProperty("nowcheck", nowCheck)
                addProperty("howmany", howMany)
            }

            simmonsAPI.setStoreNum(jsonObject).body()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getCheckStatus(): Int? {
        return try {
            simmonsAPI.getCheckStatus().body()
        } catch (e: Exception) {
            null
        }
    }
}
