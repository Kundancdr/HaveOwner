package com.example.haveneraowner.data.models.response

data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String
)