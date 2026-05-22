package com.swastik.haveneraowner.data.models.response

import com.google.gson.annotations.SerializedName

data class SigninResponseModel(

    @SerializedName("refresh")
    val refreshToken: String,

    @SerializedName("access")
    val accessToken: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    val lastName: String,

    @SerializedName("role")
    val role: String,

    @SerializedName("detail")
    val detail: String
)