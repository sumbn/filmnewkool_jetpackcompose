package com.example.appnewkool.di

import android.content.Context
import androidx.room.Room
import com.example.appnewkool.data.database.AppDB
import com.example.appnewkool.data.database.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDB {
        return Room.databaseBuilder(context, AppDB::class.java,"app_db").build()
    }

    @Provides
    fun provideCustomerDao(appDatabase: AppDB): ProductDao {
        return appDatabase.productDao()
    }
}