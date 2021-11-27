package com.lovoo.lovoooffice.presentation.officedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lovoo.lovoooffice.common.base.state.Resource
import com.lovoo.lovoooffice.core.domain.model.Office
import com.lovoo.lovoooffice.core.domain.model.OfficeBooking
import com.lovoo.lovoooffice.core.domain.usecases.office.GetOfficesUseCase
import com.lovoo.lovoooffice.core.domain.usecases.office.OfficeBookingsDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

enum class NavFlow{
    DEFAULT,
    BOOK_OFFICE,
}

class OfficeDetailsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState

    private val _uiActions = MutableStateFlow(Pair(NavFlow.DEFAULT,UIState()))
    val uiActions: StateFlow<Pair<NavFlow,UIState>> = _uiActions

    inner class UIState{
        var office : Office? = null
    }

    fun setOfficeDetails(office : Office){
        _uiState.value = _uiState.value?.apply {
            this.office = office
        }
    }

}