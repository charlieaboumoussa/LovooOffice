package com.lovoo.lovoooffice.core.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.lovoo.lovoooffice.common.base.database.BaseDao
import com.lovoo.lovoooffice.core.data.database.model.office.OfficeDto
import kotlinx.coroutines.flow.Flow

@Dao
interface OfficeDao : BaseDao<OfficeDto> {

    @Query("SELECT * FROM OFFICE")
    suspend fun getOffices() : List<OfficeDto>

    @Query("SELECT * FROM OFFICE")
    fun liveOffices(): LiveData<List<OfficeDto>>

    @Query("SELECT * FROM OFFICE")
    fun flowOffices(): Flow<List<OfficeDto>>

    @Query("DELETE FROM OFFICE")
    suspend fun nuke()

}