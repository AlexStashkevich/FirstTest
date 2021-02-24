package dev.alexstashkevich.firsttest.utils

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.alexstashkevich.firsttest.R

fun AppCompatActivity.showToast(message: String) {
    if (message.isNotBlank() && message.isNotEmpty()) {
        val text = resources.getString(R.string.toast_text, message)
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}

fun AppCompatActivity.showToast() {
    Toast.makeText(this, "Empty text", Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.showToast(resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
}