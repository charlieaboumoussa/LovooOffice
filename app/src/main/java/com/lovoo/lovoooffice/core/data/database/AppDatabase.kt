package com.example.latestmovies.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lovoo.lovoooffice.core.data.database.dao.movie.Office
import com.lovoo.lovoooffice.core.data.database.dao.movie.OfficeDao

@Database(entities = [Office::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun officeDao() : OfficeDao

}
