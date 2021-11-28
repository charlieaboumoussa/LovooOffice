package com.lovoo.lovoooffice.common.testfakes

import com.lovoo.lovoooffice.core.data.database.dao.OfficeBookingDao
import com.lovoo.lovoooffice.core.data.database.dao.OfficeFilterDao
import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeBookingDto
import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeFilterDto
import com.lovoo.lovoooffice.core.data.model.office.OfficeDto
import com.lovoo.lovoooffice.core.data.remote.OfficeService
import com.lovoo.lovoooffice.core.domain.repositories.OfficeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OfficeRepositoryFake @Inject constructor(
    private val _officesService: OfficeService,
    private val _officeBookingDao: OfficeBookingDao,
    private val _officeFilterDao: OfficeFilterDao
) : OfficeRepository {
    override suspend fun getOffices(): List<OfficeDto> {
        return arrayListOf()
    }

    override suspend fun getOfficeBookings(): List<OfficeBookingDto> {
        return arrayListOf()
    }

    override suspend fun getRemoteOfficeFilters(): List<OfficeFilterDto> {
        return arrayListOf()
    }

    override fun flowOfficeBookings(): Flow<List<OfficeBookingDto>> {
        return flow {  }
    }

    override fun flowOfficeBookingsByOfficeId(officeId: String): Flow<List<OfficeBookingDto>> {
        return flow {  }
    }

    override fun flowOfficeFilters(): Flow<List<OfficeFilterDto>> {
        return flow {  }
    }

    override suspend fun deleteOfficeBooking(officeId: String) {

    }

    override suspend fun insertOfficeBooking(officeBookingDto: OfficeBookingDto) {

    }

    override suspend fun insertFilters(filters: List<OfficeFilterDto>) {

    }
}