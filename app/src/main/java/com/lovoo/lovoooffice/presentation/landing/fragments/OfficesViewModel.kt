package com.lovoo.lovoooffice.presentation.landing.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lovoo.lovoooffice.common.base.state.Resource
import com.lovoo.lovoooffice.core.domain.usecases.GetOfficesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OfficesViewModel @Inject constructor(
    private val getOfficesUseCase: GetOfficesUseCase
) : ViewModel() {

    inner class UIState{

    }

    init {
        getOffices()
    }

    fun getOffices() {
        getOfficesUseCase().onEach { result ->
            when(result) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    val offices = result.data ?: emptyList()
                }
                is Resource.Error -> {

                }
            }
        }.launchIn(viewModelScope)
    }

}