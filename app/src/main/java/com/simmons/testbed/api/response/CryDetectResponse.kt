package com.simmons.testbed.api.response

import com.google.gson.annotations.SerializedName

data class CryDetectResponse(
    @SerializedName("new_id") val id: Int,
    @SerializedName("result") val result: Int
)
