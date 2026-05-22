package com.swastik.haveneraowner.data.models.request

import com.google.gson.annotations.SerializedName

data class LoginOtRequestModel(
    @SerializedName("email")  val email: String? = null,
    @SerializedName("password") val password: String? = null,
    @SerializedName("otp")  val otp: String? = null
)