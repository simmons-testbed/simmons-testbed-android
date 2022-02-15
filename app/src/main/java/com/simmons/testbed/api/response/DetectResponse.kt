package com.simmons.testbed.api.response

import com.google.gson.annotations.SerializedName

data class DetectResponse(
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("id") val id: Int,
    @SerializedName("result") val result: Int,
    @SerializedName("sound") val sound: String
)
