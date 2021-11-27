package com.lovoo.lovoooffice.core.domain.repositories

import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeBookingDto
import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeFilterDto
import com.lovoo.lovoooffice.core.data.model.office.OfficeDto
import kotlinx.coroutines.flow.Flow

interface OfficeRepository {

    interface IGetOfficeFilters{
        fun onGetFiltersSuccess(filters : List<OfficeFilterDto>)
        fun onGetFiltersFailed(errorMessage : String)
    }

    suspend fun getOffices(): List<OfficeDto>

    suspend fun getOfficeBookings(): List<OfficeBookingDto>

    suspend fun getOfficeFilters(getOfficeFiltersInterface: IGetOfficeFilters)

    fun flowOfficeBookings(): Flow<List<OfficeBookingDto>>

    fun flowOfficeFilters(): Flow<List<OfficeFilterDto>>

    suspend fun deleteOfficeBooking(officeId: String)

    suspend fun insertOfficeBooking(officeBookingDto: OfficeBookingDto)

    suspend fun insertFilters(filters : List<OfficeFilterDto>)
}