package com.example.haveneraowner.data.models.request

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(
    @SerializedName("password") val password: String? = null,
    @SerializedName("password1")  val password1: String? = null
)