package dev.alexstashkevich.firsttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dev.alexstashkevich.firsttest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "start of onCreate function")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myText = "Hello, world!"
        val myNumber = 42
        val myFloatingNumber = 3.14

        val outputText = "$myText $myNumber $myFloatingNumber"
        binding.tvOutputText.text = outputText
        
        Log.d(TAG, "end  of onCreate function")
    }

    override fun onStart() {
        super.onStart()

        Log.e(TAG, "activity onStart")
    }

    override fun onResume() {
        super.onResume()

        Log.w(TAG, "activity onResume")
    }

    override fun onPause() {
        super.onPause()

        Log.w(TAG, "activity onPause")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.e(TAG, "activity onDestroy")
    }

    companion object {
        private const val TAG = "netology voice"

    }
}