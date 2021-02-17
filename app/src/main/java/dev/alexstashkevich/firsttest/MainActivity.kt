package dev.alexstashkevich.firsttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import dev.alexstashkevich.firsttest.databinding.ActivityMainBinding
import dev.alexstashkevich.firsttest.utils.showToast

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        binding.searchButton.setOnClickListener {
            showToast(binding.questionInput.text.toString())
        }
    }
}