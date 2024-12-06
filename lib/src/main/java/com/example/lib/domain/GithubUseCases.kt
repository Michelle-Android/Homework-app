package com.example.lib.domain

import com.example.lib.network.model.Request
import javax.inject.Inject

interface GithubUseCases {
    suspend fun login(account: String, pwd: String)
}


class GithubUseCasesImpl @Inject constructor(
    private val repository: GithubRepository
): GithubUseCases {
    override suspend fun login(account: String, pwd: String) {
        repository.login(Request(clientId = account, clientSecret = pwd))
    }
}

