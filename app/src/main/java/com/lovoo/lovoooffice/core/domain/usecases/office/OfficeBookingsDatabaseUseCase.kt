package com.lovoo.lovoooffice.core.domain.usecases.office

import com.lovoo.lovoooffice.common.base.state.Resource
import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeBookingDtoMapper
import com.lovoo.lovoooffice.core.domain.model.OfficeBooking
import com.lovoo.lovoooffice.core.domain.repositories.OfficeRepository
import com.lovoo.lovoooffice.core.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class OfficeBookingsDatabaseUseCase @Inject constructor(
    private val _repository: OfficeRepository,
    private val _officeBookingDtoMapper: OfficeBookingDtoMapper,
): BaseUseCase<Boolean>() {

    sealed class BookingsDatabaseOperation(val data : OfficeBooking){
        class Insert(booking : OfficeBooking) : BookingsDatabaseOperation(booking)
        class Delete(booking : OfficeBooking) : BookingsDatabaseOperation(booking)
    }

    override fun useCaseName(): String = "OfficeBookingsDatabaseUseCase"

    operator fun invoke(databaseOperation : BookingsDatabaseOperation): Flow<Resource<Boolean>> = baseFlow { baseFlow->
        val bookingDto = _officeBookingDtoMapper.mapFromDomainModel(databaseOperation.data)
        when(databaseOperation){
            is BookingsDatabaseOperation.Insert->{
                _repository.insertOfficeBooking(bookingDto)
            }
            is BookingsDatabaseOperation.Delete->{
                _repository.deleteOfficeBooking(bookingDto.officeId)
            }
        }
        baseFlow.emit(Resource.Success(true))
    }

}
