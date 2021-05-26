package com.g_one_nursesapp.entity

import com.g_one_nursesapp.api.response.HospitalsResponse

data class UserEntity(
    val accountType: String? = "NURSE",
    val email: String? = "",
    val groupName: String? = "",
    val noHp: String? ="",
    val password: String? = "",
)