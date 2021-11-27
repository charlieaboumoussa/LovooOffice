package com.lovoo.lovoooffice.core.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.lovoo.lovoooffice.common.base.database.BaseDao
import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeBookingDto
import kotlinx.coroutines.flow.Flow

@Dao
interface OfficeBookingDao : BaseDao<OfficeBookingDto> {

    @Query("SELECT * FROM OFFICE_BOOKING")
    suspend fun getOfficeBookings() : List<OfficeBookingDto>

    @Query("SELECT * FROM OFFICE_BOOKING")
    fun liveOfficeBookings(): LiveData<List<OfficeBookingDto>>

    @Query("SELECT * FROM OFFICE_BOOKING")
    fun flowOfficeBookings(): Flow<List<OfficeBookingDto>>

    @Query("DELETE FROM OFFICE_BOOKING WHERE OFFICE_ID = :id")
    suspend fun deleteBookingById(id : String)

    @Query("DELETE FROM OFFICE_BOOKING")
    suspend fun nuke()

}