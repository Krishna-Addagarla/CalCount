package com.example.calcount.Data.Local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CalItemsDao {

    @Upsert
    suspend fun addItem(item : CalItems)

    @Query("DELETE FROM CalItems WHERE id = :id")
    suspend fun deleteItem(id : Int)

    @Query("Select * From CalItems WHERE dateid = :dateid")
    fun getItems(dateid : Int) : Flow<List<CalItems>>
}