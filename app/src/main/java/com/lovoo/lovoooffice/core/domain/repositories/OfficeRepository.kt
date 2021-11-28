package com.lovoo.lovoooffice.core.domain.repositories

import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeBookingDto
import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeFilterDto
import com.lovoo.lovoooffice.core.data.model.office.OfficeDto
import kotlinx.coroutines.flow.Flow

interface OfficeRepository {

    suspend fun getOffices(): List<OfficeDto>

    suspend fun getOfficeBookings(): List<OfficeBookingDto>

    suspend fun getRemoteOfficeFilters() : List<OfficeFilterDto>

    fun flowOfficeBookings(): Flow<List<OfficeBookingDto>>

    fun flowOfficeBookingsByOfficeId(officeId: String): Flow<List<OfficeBookingDto>>

    fun flowOfficeFilters(): Flow<List<OfficeFilterDto>>

    suspend fun deleteOfficeBooking(officeId: String)

    suspend fun insertOfficeBooking(officeBookingDto: OfficeBookingDto)

    suspend fun insertFilters(filters : List<OfficeFilterDto>)
}