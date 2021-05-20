package com.g_one_nursesapp.entity

data class UserEntity(

    val id: String? = "",
    val email: String? = "",
    val groupName: String? = "",
    val noHp: String? ="",
    val password: String? = "",
    val isDeleted: Boolean? = false,
    val createdAt: String? = "",
    val updatedAt: String? = "",

    )