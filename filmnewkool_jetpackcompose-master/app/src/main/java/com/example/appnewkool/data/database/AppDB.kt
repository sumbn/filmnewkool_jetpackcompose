package com.example.appnewkool.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appnewkool.data.database.dao.ProductDao
import com.example.appnewkool.data.database.entities.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class  AppDB : RoomDatabase() {
    abstract fun productDao(): ProductDao
}