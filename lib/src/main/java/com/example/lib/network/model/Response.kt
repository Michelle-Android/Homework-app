package com.example.lib.network.model

data class AccessTokenResponse(
    val accessToken: String,
    val scope: String,
    val tokenType: String
)