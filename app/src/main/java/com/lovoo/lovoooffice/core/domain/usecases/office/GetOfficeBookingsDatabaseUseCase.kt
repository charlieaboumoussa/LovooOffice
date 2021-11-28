package com.lovoo.lovoooffice.core.domain.usecases.office

import com.lovoo.lovoooffice.common.base.state.Resource
import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeBookingDtoMapper
import com.lovoo.lovoooffice.core.domain.model.OfficeBooking
import com.lovoo.lovoooffice.core.domain.repositories.OfficeRepository
import com.lovoo.lovoooffice.core.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class GetOfficeBookingsDatabaseUseCase @Inject constructor(
    private val _repository: OfficeRepository,
    private val _officeBookingDtoMapper: OfficeBookingDtoMapper,
): BaseUseCase<List<OfficeBooking>>() {

    sealed class GetBookingsDatabaseOperation(val data : OfficeBooking){
        class GetFlowById(booking : OfficeBooking) : GetBookingsDatabaseOperation(booking)
    }

    override fun useCaseName(): String = "OfficeBookingsDatabaseUseCase"

    operator fun invoke(databaseOperation : GetBookingsDatabaseOperation): Flow<Resource<List<OfficeBooking>>> = baseFlow { baseFlow->
        val bookingDto = _officeBookingDtoMapper.mapFromDomainModel(databaseOperation.data)
        when(databaseOperation){
            is GetBookingsDatabaseOperation.GetFlowById->{
                _repository.flowOfficeBookingsByOfficeId(bookingDto.officeId).collect {
                    val officeBookings = it.map {
                        _officeBookingDtoMapper.mapToDomainModel(it)
                    }
                    baseFlow.emit(Resource.Success(officeBookings))
                }
            }
        }
    }

}
