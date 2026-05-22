package com.swastik.haveneraowner.data.models.response

data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String
)