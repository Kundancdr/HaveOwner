package com.example.haveneraowner.data.models.request

import com.google.gson.annotations.SerializedName

data class SigninRequestModel(
    @SerializedName("email") val email: String? = null,
    @SerializedName("password") val password: String? = null
)