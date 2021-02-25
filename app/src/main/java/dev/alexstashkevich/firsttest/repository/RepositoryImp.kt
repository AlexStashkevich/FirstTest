package dev.alexstashkevich.firsttest.repository


import android.util.Log
import dev.alexstashkevich.firsttest.models.RequestItem
import dev.alexstashkevich.firsttest.models.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val localRepository: ILocalRepository,
    private val remoteRepository: IRemoteRepository
) : IRepository {

    override val requestList: Flow<List<RequestItem>>
        get() = localRepository.getCachedRequest()

    override suspend fun askWolfram(question: String) {
        when (val resource = remoteRepository.askWolfram(question)) {
            is Resource.Failure -> Log.w(TAG, "resource failure reason: ${resource.message}")
            Resource.IDontUnderstand -> Log.w(TAG, "i don't understand your question")
            is Resource.Success -> {
                Log.w(TAG, "resource success answers: ${resource.answers}")
                localRepository.insert(question, resource.answers)
            }
        }
    }

    private val TAG = "RepositoryImp"
}