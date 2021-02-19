package dev.alexstashkevich.firsttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dev.alexstashkevich.firsttest.databinding.ActivityMainBinding
import com.wolfram.alpha.WAEngine
import com.wolfram.alpha.WAPlainText

class MainActivity : AppCompatActivity() {

    private val TAG by lazy { "wolfram" }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        binding.searchButton.setOnClickListener { askWolfram(binding.questionInput.text.toString()) }
    }

    private fun askWolfram(question: String) {
        val wolframAppId = "YOUR API KEY"

        val engine = WAEngine()
        engine.appID = wolframAppId
        engine.addFormat("plaintext")

        val query = engine.createQuery()
        query.input = question

        binding.answerOutput.text = getString(R.string.let_me_think)

        Thread {
            val queryResult = engine.performQuery(query)

            binding.answerOutput.post { // Возвращаемся на Main Thread
                when {
                    queryResult.isError -> {
                        Log.e(TAG, queryResult.errorMessage)
                        binding.answerOutput.text = queryResult.errorMessage
                    }
                    !queryResult.isSuccess -> {
                        Log.e(TAG, "Sorry, I don't understand, can you rephrase?")
                        binding.answerOutput.text = getString(R.string.sorry)
                    }
                    else -> {
                        queryResult.pods
                            .asSequence()
                            .filterNot { it.isError }
                            .forEach {
                                it.subpods.forEach { subpod ->
                                    subpod.contents.forEach { element ->
                                        if (element is WAPlainText) {
                                            Log.d(TAG, element.text)
                                            binding.answerOutput.text = element.text
                                        }
                                    }
                                }
                            }
                    }
                }
            }
        }.start()
    }
}