package com.lovoo.lovoooffice.core.domain.usecases.office

import com.lovoo.lovoooffice.common.base.state.Resource
import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeBookingDto
import com.lovoo.lovoooffice.core.data.model.office.OfficeDto
import com.lovoo.lovoooffice.core.data.model.office.OfficeDtoMapper
import com.lovoo.lovoooffice.core.domain.model.Filter
import com.lovoo.lovoooffice.core.domain.model.Office
import com.lovoo.lovoooffice.core.domain.repositories.OfficeRepository
import com.lovoo.lovoooffice.core.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOfficesUseCase @Inject constructor(
    private val _repository: OfficeRepository,
    private val _officeDtoMapper: OfficeDtoMapper
): BaseUseCase<List<Office>>() {

    override fun useCaseName(): String = "GetOfficesUseCase"

    operator fun invoke(): Flow<Resource<List<Office>>> = baseFlow {
        it.emit(Resource.Loading())
        val officeBookings = _repository.getOfficeBookings()
        val offices = arrayListOf<Office>()
        _repository.getOffices().forEach { officeDto->
            mergeBookingsWithOffice(officeBookings, officeDto)
            offices.add(_officeDtoMapper.mapToDomainModel(officeDto))
        }
        it.emit(Resource.Success(offices))
    }

    fun mergeBookingsWithOffice(officeBookings: List<OfficeBookingDto>, officeDto: OfficeDto){
        officeBookings.forEach {
            if(it.officeId == officeDto.id){
                if(officeDto.bookings == null){
                    officeDto.bookings = arrayListOf()
                }
                officeDto.bookings?.add(it)
            }
        }
    }
}
