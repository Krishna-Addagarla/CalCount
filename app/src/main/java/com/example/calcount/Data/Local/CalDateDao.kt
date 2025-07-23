package com.example.calcount.Data.Local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CalDateDao {

    @Query("Select * FROM CalDate ORDER BY id ASC")
    fun getCalByDate() : Flow<List<CalDate>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDate(calDate: CalDate)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDates(dates: List<CalDate>)

    @Query("""
        Update CalDate Set totalcal = 
        (select IFNULL(SUM(itemCal), 0) from CalItems where CalItems.dateid = CalDate.id) """)
    suspend fun updateTotalCal()
}