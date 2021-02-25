package dev.alexstashkevich.firsttest.repository

import android.util.Log
import com.wolfram.alpha.WAEngine
import com.wolfram.alpha.WAPlainText
import dev.alexstashkevich.firsttest.BuildConfig
import dev.alexstashkevich.firsttest.models.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteRepositoryImp : IRemoteRepository {
    override suspend fun askWolfram(question: String): Resource {
        val wolframAppId = BuildConfig.WOLFRAM_APP_ID

        val engine = WAEngine()
        engine.appID = wolframAppId
        engine.addFormat("plaintext")

        val query = engine.createQuery()
        query.input = question

        val queryResult =
            withContext(Dispatchers.IO) { engine.performQuery(query) }

        when {
            queryResult.isError -> {
                Log.e(TAG, queryResult.errorMessage)

                return Resource.Failure(queryResult.errorMessage)
            }
            !queryResult.isSuccess -> {
                Log.e(TAG, "Sorry, I don't understand, can you rephrase?")

                return Resource.IDontUnderstand
            }
            else -> {
                val answersList = mutableListOf<String>()
                queryResult.pods
                    .asSequence()
                    .filterNot { it.isError }
                    .forEach {
                        it.subpods.forEach { subPod ->
                            subPod.contents.forEach { element ->
                                if (element is WAPlainText) {
                                    Log.d(TAG, "text: ${element.text}")
                                    answersList.add(element.text)
                                }
                            }
                        }
                    }

                return Resource.Success(answersList)
            }
        }
    }

    private val TAG = "RemoteRepositoryImp"
}