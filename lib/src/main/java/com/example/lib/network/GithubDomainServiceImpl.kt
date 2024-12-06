package com.example.lib.network

import com.example.lib.network.model.AccessTokenResponse
import com.example.lib.network.model.Request

class GithubDomainServiceImpl(private val service: GithubNetworkService) :GithubNetworkService {
    override suspend fun getAccessToken(request: Request): AccessTokenResponse {
        return service.getAccessToken(request)
    }
}