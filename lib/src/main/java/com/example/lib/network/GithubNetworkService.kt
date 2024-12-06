package com.example.lib.network

import com.example.lib.network.model.AccessTokenResponse
import com.example.lib.network.model.Request
import retrofit2.http.Body
import retrofit2.http.GET

interface GithubNetworkService {
    @GET("login/oauth/access_token")
    suspend fun getAccessToken(
        @Body request: Request
    ): AccessTokenResponse
}