package com.g_one_nursesapp.auth.login

import com.g_one_nursesapp.entity.UserEntity

data class LoginResponse(val error: Boolean, val message:String, val user: UserEntity)