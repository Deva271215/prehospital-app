package com.g_one_nursesapp.api.response

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class HospitalsResponse(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("code")
    val code: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("category")
    val category: Char,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("rating")
    val rating: Double,

    @field:SerializedName("address")
    val address: String,

    @field:SerializedName("latitude")
    val latitude: Double,

    @field:SerializedName("longitude")
    val longitude: Double,

    @field:SerializedName("owner")
    val owner: String,

    @field:SerializedName("telephone_number")
    val telephoneNumber: String,

    @field:SerializedName("is_deleted")
    val isDeleted: Boolean,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("deleted_at")
    val deletedAt: String,

    @field:SerializedName("distances_from_ambulance")
    val distancesFromAmbulance: Double?
)