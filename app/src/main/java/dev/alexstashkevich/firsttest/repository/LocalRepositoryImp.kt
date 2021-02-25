package dev.alexstashkevich.firsttest.repository

import android.util.Log
import dev.alexstashkevich.firsttest.db.RequestDao
import dev.alexstashkevich.firsttest.models.RequestItem
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class LocalRepositoryImp @Inject constructor(
    private val dao: RequestDao
) : ILocalRepository {
    private val TAG = "LocalRepositoryImp"

    override fun getCachedRequest(): Flow<List<RequestItem>> = dao.getAll()

    override suspend fun insert(question: String, answers: List<String>) {
        val timestamp = Calendar.getInstance().timeInMillis
        val item = RequestItem(question, answers.joinToString(), timestamp)
        Log.w(TAG, "localRepository insert item: $item")
        dao.insert(item)
    }
}