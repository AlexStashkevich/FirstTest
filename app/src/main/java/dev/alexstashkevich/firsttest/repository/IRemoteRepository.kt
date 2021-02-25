package dev.alexstashkevich.firsttest.repository

import dev.alexstashkevich.firsttest.models.Resource

interface IRemoteRepository {

    suspend fun askWolfram(question: String): Resource
}