package com.lovoo.lovoooffice.di

import android.content.Context
import androidx.room.Room
import com.example.latestmovies.model.database.AppDatabase
import com.lovoo.lovoooffice.core.data.database.dao.OfficeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "APP_DATABASE"

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context : Context): AppDatabase{
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
//                .addMigrations()
                .build()
    }

    @Singleton
    @Provides
    fun provideOfficeDao(
        database: AppDatabase
    ): OfficeDao{
        return database.officeDao()
    }
}