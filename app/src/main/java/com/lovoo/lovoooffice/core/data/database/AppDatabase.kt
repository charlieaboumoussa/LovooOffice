package com.example.latestmovies.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lovoo.lovoooffice.core.data.database.dao.OfficeBookingDao
import com.lovoo.lovoooffice.core.data.database.dao.OfficeFilterDao
import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeBookingDto
import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeFilterDto
import com.lovoo.lovoooffice.core.data.database.typeconverters.ListConverter
import com.lovoo.lovoooffice.core.data.database.typeconverters.LovooFactConverter

@Database(entities = [OfficeBookingDto::class, OfficeFilterDto::class], version = 1)
@TypeConverters(LovooFactConverter::class, ListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun officeBookingDao() : OfficeBookingDao
    abstract fun officeFilterDao() : OfficeFilterDao

}
