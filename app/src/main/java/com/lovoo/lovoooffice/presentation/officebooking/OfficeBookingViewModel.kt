package com.lovoo.lovoooffice.presentation.officebooking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lovoo.lovoooffice.common.base.state.Resource
import com.lovoo.lovoooffice.core.domain.model.Office
import com.lovoo.lovoooffice.core.domain.model.OfficeBooking
import com.lovoo.lovoooffice.core.domain.usecases.office.GetOfficesUseCase
import com.lovoo.lovoooffice.core.domain.usecases.office.OfficeBookingsDatabaseUseCase
import com.lovoo.lovoooffice.presentation.officedetails.NavFlow
import com.lovoo.lovoooffice.presentation.officedetails.OfficeDetailsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

enum class NavFlow{
    DEFAULT,
    BOOKING_DONE,
    BOOKING_CONFLICT,
}

@HiltViewModel
class OfficeBookingViewModel @Inject constructor(
    private val _getOfficesUseCase: GetOfficesUseCase,
    private val _officeBookingDatabaseUseCase: OfficeBookingsDatabaseUseCase,
) : ViewModel() {

    private val _uiState = MutableLiveData(UIState())
    val uiState: LiveData<UIState> = _uiState

    private val _uiActions = MutableStateFlow(Pair(NavFlow.DEFAULT,UIState()))
    val uiActions: StateFlow<Pair<NavFlow, UIState>> = _uiActions

    inner class UIState{
        var office : Office? = null
    }

    fun bookOffice(bookingDate: Long, bookingPeriod: Long){
        val office = _uiState.value?.office
        office?.id?.let {
            val officeBooking = OfficeBooking(
                officeId = it,
                bookingDate = bookingDate,
                bookingPeriod = bookingPeriod
            )
            if(isBookingConflicted(officeBooking).not()){
                _officeBookingDatabaseUseCase(
                    OfficeBookingsDatabaseUseCase.BookingsDatabaseOperation.Insert(officeBooking)
                )
            }else{
                //TODO[11/22/2021] show invalid booking date (already booked)
            }
        }
    }

    private fun isBookingConflicted(officeBooking: OfficeBooking) : Boolean{
        val office = _uiState.value?.office
        office?.bookings?.forEach { anyPreviousBokking->
            val anyPreviousBookingPeriod = anyPreviousBokking.bookingDate + anyPreviousBokking.bookingPeriod
            val currentBookingPeriod = officeBooking.bookingDate + officeBooking.bookingPeriod
            if(currentBookingPeriod >= anyPreviousBokking.bookingDate
                || currentBookingPeriod <= anyPreviousBookingPeriod){
                return true
            }
        }
        return false
    }

}