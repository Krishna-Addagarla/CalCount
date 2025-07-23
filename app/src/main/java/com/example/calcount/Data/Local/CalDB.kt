package com.example.calcount.Data.Local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CalDate::class, CalItems::class],version =1)
abstract class CalDB : RoomDatabase() {

    abstract fun calDateDao(): CalDateDao
    abstract fun calItemsDao(): CalItemsDao

}