package com.lovoo.lovoooffice.presentation.landing.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lovoo.lovoooffice.common.base.state.Resource
import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeFilters
import com.lovoo.lovoooffice.core.domain.model.Filter
import com.lovoo.lovoooffice.core.domain.model.Office
import com.lovoo.lovoooffice.core.domain.usecases.office.GetOfficeFiltersUseCase
import com.lovoo.lovoooffice.core.domain.usecases.office.GetOfficesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class NavFlow{
    DEFAULT,
    APPLY_FILTERS,
    CLEAR_FILTERS
}

@HiltViewModel
class OfficesViewModel @Inject constructor(
    private val _getOfficesUseCase: GetOfficesUseCase,
    private val _getOfficeFiltersUseCase: GetOfficeFiltersUseCase
) : ViewModel() {

    ///Note: LiveData is used here instead of Flow since a Flow of UIState doesn't update the view (data binding)
    ///In case of using Flow, every field inside UIState should have its own Flow
    private val _uiState = MutableLiveData(UIState())
    val uiState: LiveData<UIState> = _uiState

    private val _uiActions = MutableStateFlow(Pair(NavFlow.DEFAULT,UIState()))
    val uiActions: StateFlow<Pair<NavFlow, UIState>> = _uiActions

    inner class UIState{
        var offices : ArrayList<Office>? = null
        var filteredOffices : ArrayList<Office>? = null
        var filters : List<OfficeFilters>? = null
        var filtersToApply : HashMap<String, Filter> = hashMapOf()
        var appliedFilters : HashMap<String, Filter> = hashMapOf()
    }

    init {
        getOffices()
        getFilters()
    }

    fun getOffices() {
        _getOfficesUseCase().onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _uiState.value?.let {
                        _uiState.value = _uiState.value?.apply {
                            this.offices?.clear()
                        }
                    }
                }
                is Resource.Success -> {
                    val offices = result.data ?: emptyList()
                    val appliedFilters = uiState.value?.appliedFilters
                    appliedFilters?.let {
                        if(it.size > 0){
                            val filteredOffices = arrayListOf<Office>()
                            it.forEach {
                                offices.forEach { office->
                                    if(it.value.category.equals(office.department)
                                        && it.value.category.equals(office.type)){
                                        filteredOffices.add(office)
                                    }else if(it.value.category.equals(office.department)){
                                        filteredOffices.add(office)
                                    }else if(it.value.category.equals(office.type)){
                                        filteredOffices.add(office)
                                    }
                                }
                            }
                            _uiState.value = _uiState.value?.apply {
                                this.filteredOffices = filteredOffices
                            }
                        }else{
                            _uiState.value = _uiState.value?.apply {
                                this.offices = ArrayList(offices)
                                this.filteredOffices = ArrayList(offices)
                            }
                        }
                    }
                }
                is Resource.Error -> {

                }
            }
        }.launchIn(viewModelScope)
    }

    fun getFilters(){
        _getOfficeFiltersUseCase().onEach { result ->
            when(result) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    val filters = result.data ?: emptyList()
                    _uiState.value = _uiState.value?.apply {
                        this.filters = filters
                    }
                }
                is Resource.Error -> {

                }
            }
        }.launchIn(viewModelScope)
    }

    fun addFilter(filter : Filter){
        _uiState.value = _uiState.value?.apply {
            this.filtersToApply[filter.category] = filter
        }
    }

    fun applyFilters(){
        _uiState.value?.filtersToApply?.let {
            _uiState.value?.appliedFilters = it
        }
        viewModelScope.launch {
            _uiState.value?.let {
                _uiActions.emit(Pair(NavFlow.APPLY_FILTERS, it))
            }
        }
    }

    fun clearFilters(){
        _uiState.value?.filteredOffices = _uiState.value?.offices
        _uiState.value?.filtersToApply = hashMapOf()
        _uiState.value?.filtersToApply = hashMapOf()
        _uiState.value?.appliedFilters = hashMapOf()
        viewModelScope.launch {
            _uiState.value?.let {
                _uiActions.value = Pair(NavFlow.APPLY_FILTERS, it)
            }
        }
    }
}