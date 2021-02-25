package dev.alexstashkevich.firsttest.repository

import dev.alexstashkevich.firsttest.models.RequestItem
import kotlinx.coroutines.flow.Flow

interface IRepository {

    val requestList: Flow<List<RequestItem>>

    suspend fun askWolfram(question: String)
}