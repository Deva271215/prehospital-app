package com.g_one_nursesapp.api.response

import com.google.gson.annotations.SerializedName

data class ChatResponse(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("user")
    val user: UserResponse,

    @field:SerializedName("hospital")
    val hospital: HospitalsResponse,

    @field:SerializedName("is_deleted")
    val isDeleted: Boolean,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("deleted_at")
    val deletedAt: String,
)