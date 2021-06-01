package com.g_one_nursesapp.api.response

import com.google.gson.annotations.SerializedName

data class PredictionResponse(
    @field:SerializedName("label")
    val label: String,

    @field:SerializedName("value")
    val value: Double
)