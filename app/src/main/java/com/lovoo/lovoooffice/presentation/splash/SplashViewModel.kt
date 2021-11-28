package com.lovoo.lovoooffice.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lovoo.lovoooffice.R
import com.lovoo.lovoooffice.common.base.state.Resource
import com.lovoo.lovoooffice.common.base.viewmodels.BaseViewModel
import com.lovoo.lovoooffice.core.domain.usecases.office.GetOfficeFiltersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

enum class NavFlow{
    DEFAULT,
    START_APP
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val _getOfficeFiltersUseCase: GetOfficeFiltersUseCase
) : BaseViewModel() {

    private val _uiActions = MutableStateFlow(Pair(NavFlow.DEFAULT,UIState()))
    val uiActions: StateFlow<Pair<NavFlow, UIState>> = _uiActions

    inner class UIState{

    }

    fun getOfficeFilters() {
        _getOfficeFiltersUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _uiActions.emit(Pair(NavFlow.START_APP, UIState()))
                }
                is Resource.Error -> {
                    result.message?.let {
                        showError(RemoteExceptions.DEFAULT)
                    }
                    _uiActions.emit(Pair(NavFlow.START_APP, UIState()))
                }
                else->{}
            }
        }.launchIn(viewModelScope)
    }
}