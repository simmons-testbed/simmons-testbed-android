package com.simmons.testbed.model

import com.google.gson.JsonObject
import com.simmons.testbed.api.SimmonsAPI
import com.simmons.testbed.api.response.CryDetectResponse
import com.simmons.testbed.api.response.DetectResponse
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

    suspend fun cryDetect(cryType: Int): Int? {
        return try {
            val jsonObject = JsonObject().apply {
                addProperty("sound", cryType)
            }

            simmonsAPI.detectCry(jsonObject).body()?.id
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getDetect(id: Int): Int? {
        return try {
            simmonsAPI.getDetect(id).body()?.result
        } catch (e: Exception) {
            null
        }
    }
}
