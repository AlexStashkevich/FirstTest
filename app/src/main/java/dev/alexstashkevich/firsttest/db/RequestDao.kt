package dev.alexstashkevich.firsttest.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.alexstashkevich.firsttest.models.RequestItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface RequestDao {
    @Query("Select * FROM request_table ORDER BY timestamp DESC")
    fun getAll(): Flow<List<RequestItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: RequestItem)
}