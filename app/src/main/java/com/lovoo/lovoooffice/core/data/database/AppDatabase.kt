package com.example.latestmovies.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lovoo.lovoooffice.core.data.database.model.office.OfficeDto
import com.lovoo.lovoooffice.core.data.database.dao.OfficeDao

@Database(entities = [OfficeDto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun officeDao() : OfficeDao

}
