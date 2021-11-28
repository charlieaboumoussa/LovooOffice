package com.lovoo.lovoooffice.presentation.officedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lovoo.lovoooffice.common.base.viewmodels.BaseViewModel
import com.lovoo.lovoooffice.core.domain.model.Office
import com.lovoo.lovoooffice.core.domain.model.OfficeBooking
import com.lovoo.lovoooffice.core.domain.usecases.office.GetOfficeBookingsDatabaseUseCase
import com.lovoo.lovoooffice.core.domain.usecases.office.OfficeBookingsDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class NavFlow{
    DEFAULT,
    BOOK_OFFICE,
}

@HiltViewModel
class OfficeDetailsViewModel @Inject constructor(
    private val _getOfficeBookingDatabaseUseCase: GetOfficeBookingsDatabaseUseCase
) : BaseViewModel() {

    ///Note: LiveData is used here instead of Flow since a Flow of UIState doesn't update the view (data binding)
    ///In case of using Flow, every field inside UIState should have its own Flow
    private val _uiState = MutableLiveData(UIState())
    val uiState: LiveData<UIState> = _uiState

    private val _uiActions = MutableStateFlow(Pair(NavFlow.DEFAULT,UIState()))
    val uiActions: StateFlow<Pair<NavFlow,UIState>> = _uiActions

    inner class UIState{
        var office : Office? = null
        var isOfTypeMeeting : Boolean? = null
        var bookings : List<OfficeBooking>? = null
    }

    fun setOfficeDetails(office : Office){
        _uiState.value = _uiState.value?.apply {
            this.office = office
            office.type?.let {
                isOfTypeMeeting = if(it.equals("meeting")){
                    getOfficeBookingsByOfficeId()
                    true
                }else{
                    false
                }
            }
        }
    }

    fun getOfficeBookingsByOfficeId(){
        viewModelScope.launch {
            _uiState.value?.let { uiState->
                uiState.office?.let {
                    val officeBooking = OfficeBooking()
                    officeBooking.officeId = it.id
                    _getOfficeBookingDatabaseUseCase(GetOfficeBookingsDatabaseUseCase.GetBookingsDatabaseOperation.GetFlowById(officeBooking)).collect {
                        uiState.bookings = it.data ?: emptyList()
                        _uiState.value = uiState
                    }
                }
            }
        }
    }

}