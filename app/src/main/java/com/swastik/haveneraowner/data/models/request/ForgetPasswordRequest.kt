package com.swastik.haveneraowner.data.models.request

import com.google.gson.annotations.SerializedName

data class ForgetPasswordRequest(
    @SerializedName("email") val email: String? = null
)