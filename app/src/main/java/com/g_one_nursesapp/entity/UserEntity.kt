package com.g_one_nursesapp.entity

import com.google.gson.annotations.SerializedName

data class UserEntity(
    @field:SerializedName("account_type")
    val accountType: String? = "NURSE",

    @field:SerializedName("email")
    val email: String? = "",

    @field:SerializedName("group_name")
    val groupName: String? = "",

    @field:SerializedName("telp")
    val noHp: String? ="",

    @field:SerializedName("password")
    val password: String? = "",
)