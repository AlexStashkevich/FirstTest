package dev.alexstashkevich.firsttest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.alexstashkevich.firsttest.models.RequestItem

@Database(entities = [(RequestItem::class)], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun requestDao(): RequestDao
}