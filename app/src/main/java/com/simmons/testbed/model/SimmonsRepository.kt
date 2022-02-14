package com.simmons.testbed.model

import com.simmons.testbed.api.SimmonsAPI

class SimmonsRepository(private val simmonsAPI: SimmonsAPI) {

    suspend fun setBound(xBoundary: String, yBoundary: String): Int? {
        return try {
            simmonsAPI.setBound(xBoundary, yBoundary).body()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun setStoreNum(nowCheck: String, howMany: String): Int? {
        return try {
            simmonsAPI.setStoreNum(nowCheck, howMany).body()
        } catch (e: Exception) {
            null
        }
    }

    fun getCheckStatus(): Int? {
        return try {
            simmonsAPI.getCheckStatus().body()
        } catch (e: Exception) {
            null
        }
    }
}
