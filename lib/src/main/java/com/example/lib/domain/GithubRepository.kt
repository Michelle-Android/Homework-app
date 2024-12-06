package com.example.lib.domain

import com.example.lib.network.model.Request

interface GithubRepository {
    suspend fun login(loginRequest: Request): Unit
}