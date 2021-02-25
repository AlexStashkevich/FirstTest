package dev.alexstashkevich.firsttest.repository

import dev.alexstashkevich.firsttest.models.RequestItem
import kotlinx.coroutines.flow.Flow

interface ILocalRepository {
    fun getCachedRequest(): Flow<List<RequestItem>>

    suspend fun insert(question: String, answers: List<String>)
}