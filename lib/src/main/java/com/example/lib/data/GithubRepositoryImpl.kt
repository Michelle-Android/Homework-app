package com.example.lib.data

import com.example.lib.domain.GithubRepository
import com.example.lib.network.GithubNetworkService
import com.example.lib.network.model.Request

class GithubRepositoryImpl(private val service: GithubNetworkService) : GithubRepository {
    override suspend fun login(loginRequest: Request) {
        service.getAccessToken(loginRequest)
    }
}