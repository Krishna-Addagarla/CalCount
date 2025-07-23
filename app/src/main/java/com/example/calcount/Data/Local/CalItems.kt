package com.example.calcount.Data.Local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CalDate::class,
            parentColumns = ["id"],
            childColumns = ["dateid"]
        )
    ]
)
data class CalItems(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val itemName : String,
    val itemCal : Double,
    val dateid : Int
)
