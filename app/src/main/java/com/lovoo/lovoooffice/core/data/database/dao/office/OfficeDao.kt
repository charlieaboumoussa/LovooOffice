package com.lovoo.lovoooffice.core.data.database.dao.movie

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.lovoo.lovoooffice.common.base.database.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface OfficeDao : BaseDao<Office> {

    @Query("SELECT * FROM OFFICE")
    suspend fun getOffices() : List<Office>

    @Query("SELECT * FROM OFFICE")
    fun liveOffices(): LiveData<List<Office>>

    @Query("SELECT * FROM OFFICE")
    fun flowOffices(): Flow<List<Office>>

    @Query("DELETE FROM OFFICE")
    suspend fun nuke()

}