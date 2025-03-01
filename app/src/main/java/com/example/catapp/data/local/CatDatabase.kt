package com.example.catapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CatEntity::class, FavoriteEntity::class], version = 1, exportSchema = false)
abstract class CatDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao
}
