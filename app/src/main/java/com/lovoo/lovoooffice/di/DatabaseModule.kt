package com.lovoo.lovoooffice.di

import android.content.Context
import androidx.room.Room
import com.example.latestmovies.model.database.AppDatabase
import com.lovoo.lovoooffice.core.data.database.dao.OfficeBookingDao
import com.lovoo.lovoooffice.core.data.database.dao.OfficeFilterDao
import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeBookingDtoMapper
import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeFilterDtoMapper
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
    fun provideOfficeBookingDao(
        database: AppDatabase
    ): OfficeBookingDao{
        return database.officeBookingDao()
    }

    @Singleton
    @Provides
    fun provideOfficeFilterDao(
        database: AppDatabase
    ): OfficeFilterDao{
        return database.officeFilterDao()
    }

    @Singleton
    @Provides
    fun provideOfficeBookingDtoMapper(): OfficeBookingDtoMapper {
        return OfficeBookingDtoMapper()
    }

    @Singleton
    @Provides
    fun provideOfficeFilterDtoMapper(): OfficeFilterDtoMapper {
        return OfficeFilterDtoMapper()
    }
}