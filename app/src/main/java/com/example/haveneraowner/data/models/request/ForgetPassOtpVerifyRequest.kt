package com.example.haveneraowner.data.models.request

import com.google.gson.annotations.SerializedName

data class ForgetPassOtpVerifyRequest(
    @SerializedName("email") val email: String? = null,
    @SerializedName("otp")  val otp: String? = null
)