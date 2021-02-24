package dev.alexstashkevich.firsttest

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import dev.alexstashkevich.firsttest.databinding.ActivityMainBinding
import com.wolfram.alpha.WAEngine
import com.wolfram.alpha.WAPlainText
import dev.alexstashkevich.firsttest.utils.showToast
import java.util.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private val TAG by lazy { "wolfram" }

    private lateinit var binding: ActivityMainBinding
    private lateinit var textToSpeech: TextToSpeech
    private var speechRequest by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        binding.searchButton.setOnClickListener { askWolfram(binding.questionInput.text.toString()) }

        binding.speakButton.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US)
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "What do you want to know?")
            try {
                startActivityForResult(intent, 1)
            } catch (a: ActivityNotFoundException) {
                showToast("Sorry your device not supported")
            }
        }

        textToSpeech = TextToSpeech(this, {})
        textToSpeech.language = Locale.US

        speechRequest = 0

        binding.readAnswer.setOnClickListener {
            val answer = binding.answerOutput.text.toString()
            textToSpeech.speak(answer, TextToSpeech.QUEUE_ADD, null, speechRequest.toString())
            speechRequest += 1
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == RESULT_OK && data != null) {
                val result: ArrayList<String>? = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                val question: String? = result?.get(0)

                question?.let {
                    binding.questionInput.setText(it)
                    askWolfram(it)
                }
            }
        }
    }

    private fun askWolfram(question: String) {
        val wolframAppId = "T7H5XK-WG2JLPHK8J"

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

                                            textToSpeech.speak(binding.answerOutput.text, TextToSpeech.QUEUE_ADD, null, speechRequest.toString())
                                            speechRequest += 1
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