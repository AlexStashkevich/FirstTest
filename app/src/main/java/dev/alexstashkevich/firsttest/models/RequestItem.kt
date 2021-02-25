package dev.alexstashkevich.firsttest.models

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "request_table")
data class RequestItem(
    val request: String,
    val response: String,
    @PrimaryKey val timestamp: Long
) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RequestItem>() {
            override fun areItemsTheSame(oldItem: RequestItem, newItem: RequestItem): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: RequestItem, newItem: RequestItem): Boolean =
                oldItem == newItem
        }
    }
}