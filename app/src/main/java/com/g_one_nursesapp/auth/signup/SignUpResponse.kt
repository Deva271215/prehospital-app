package com.g_one_nursesapp.auth.signup

import com.google.gson.annotations.SerializedName

data class SignUpResponse (

        @field:SerializedName("id")
        val id: String,

        @field:SerializedName("group_name")
        val groupName: String,

        @field:SerializedName("email")
        val email: String,

        @field:SerializedName("is_deleted")
        val isDeleted: Boolean,

        @field:SerializedName("created_at")
        val created_at: String,

        @field:SerializedName("updated_at")
        val updated_at: String,
)

