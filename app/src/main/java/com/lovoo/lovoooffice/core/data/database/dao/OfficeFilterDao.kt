package com.lovoo.lovoooffice.core.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.lovoo.lovoooffice.common.base.database.BaseDao
import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeFilterDto
import kotlinx.coroutines.flow.Flow

@Dao
interface OfficeFilterDao : BaseDao<OfficeFilterDto> {

    @Query("SELECT * FROM OFFICE_FILTER")
    suspend fun getOfficeFilters() : List<OfficeFilterDto>

    @Query("SELECT * FROM OFFICE_FILTER")
    fun liveOfficeFilters(): LiveData<List<OfficeFilterDto>>

    @Query("SELECT * FROM OFFICE_FILTER")
    fun flowOfficeFilters() : Flow<List<OfficeFilterDto>>

    @Query("DELETE FROM OFFICE_FILTER")
    suspend fun nuke()

}