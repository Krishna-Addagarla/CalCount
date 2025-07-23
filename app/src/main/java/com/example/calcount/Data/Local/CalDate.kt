package com.example.calcount.Data.Local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CalDate(
    @PrimaryKey(autoGenerate = true)
    val id : Int=0,
    val date : String,
    var totalcal : Double
)
