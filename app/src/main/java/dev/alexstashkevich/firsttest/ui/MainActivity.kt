package dev.alexstashkevich.firsttest.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dev.alexstashkevich.firsttest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.alexstashkevich.firsttest.utils.showToast
import kotlinx.coroutines.flow.collect
import java.util.*
import kotlin.properties.Delegates

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), dev.alexstashkevich.firsttest.listener.TextToSpeech {

    private val TAG by lazy { "wolfram" }
    private val mAdapter by lazy { RequestAdapter(this) }
    private val mViewModel: MainViewModel by viewModels()
    private var speechRequest by Delegates.notNull<Int>()

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var binding: ActivityMainBinding
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        init()
        setupView()
        setupObserver()
    }

    private fun init() {
        speechRequest = 0
        textToSpeech = TextToSpeech(this, {}).apply {
            language = Locale.US
        }
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK && it.data != null) {
                    val result: ArrayList<String>? =
                        it.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    val question: String? = result?.get(0)

                    question?.let {
                        binding.questionInput.setText(it)
                        mViewModel.askWolfram(it)
                    }
                }
            }
    }

    private fun setupView() {
        binding.rViewRequest.apply {
            adapter = mAdapter
        }

        binding.searchButton.setOnClickListener {
            mViewModel.askWolfram(binding.questionInput.text.toString())
            binding.questionInput.text?.clear()
        }

        binding.speakButton.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US)
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "What do you want to know?")
            try {
                resultLauncher.launch(intent)
            } catch (a: ActivityNotFoundException) {
                showToast("Sorry your device not supported")
            }
        }

        binding.stopReading.setOnClickListener {
            textToSpeech.stop()
        }
    }

    private fun setupObserver() {
        lifecycleScope.launchWhenStarted {
            mViewModel.requestList.collect {
                Log.w(TAG, "collected list: $it")
                mAdapter.submitList(it)
            }
        }
    }

    override fun speech(answer: String) {
        textToSpeech.speak(answer, TextToSpeech.QUEUE_ADD, null, speechRequest.toString())
        speechRequest.inc()
    }
}