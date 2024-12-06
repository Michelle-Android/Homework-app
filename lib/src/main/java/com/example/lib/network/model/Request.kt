package com.example.lib.network.model

import com.google.gson.annotations.SerializedName

data class Request(
    @SerializedName("client_id")
    val clientId: String,
    @SerializedName("client_secret")
    val clientSecret: String,
    @SerializedName("code")
    val code: String = "",
    @SerializedName("redirect_uri")
    val redirectUri: String = "",
    @SerializedName("grant_type")
    val grantType: String = "authorization_code",
)