package com.lovoo.lovoooffice.presentation.landing.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lovoo.lovoooffice.common.base.state.Resource
import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeFilters
import com.lovoo.lovoooffice.core.domain.model.Filter
import com.lovoo.lovoooffice.core.domain.model.Office
import com.lovoo.lovoooffice.core.domain.usecases.office.GetOfficesUseCase
import com.lovoo.lovoooffice.common.base.viewmodels.BaseViewModel
import com.lovoo.lovoooffice.core.domain.usecases.office.GetOfficeFiltersDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class NavFlow{
    DEFAULT,
    SHOW_APPLIED_FILTERS,
    APPLY_FILTERS,
    CLEAR_FILTERS
}

@HiltViewModel
class OfficesViewModel @Inject constructor(
    private val _getOfficesUseCase: GetOfficesUseCase,
    private val _getOfficeFiltersDatabaseUseCase: GetOfficeFiltersDatabaseUseCase
) : BaseViewModel() {

    ///Note: LiveData is used here instead of Flow since a Flow of UIState doesn't update the view (data binding)
    ///In case of using Flow, every field inside UIState should have its own Flow
    private val _uiState = MutableLiveData(UIState())
    val uiState: LiveData<UIState> = _uiState

    private val _uiActions = MutableSharedFlow<Pair<NavFlow, UIState>>(0)
    val uiActions: SharedFlow<Pair<NavFlow, UIState>> = _uiActions

    inner class UIState{
        var isLoading : Boolean? = null
        var offices : ArrayList<Office>? = null
        var filteredOffices : ArrayList<Office>? = null
        var filtersMap : HashMap<String, OfficeFilters>? = null
        var shouldUpdateFilters : Boolean? = null
        var filtersToApply : HashMap<String, Filter> = hashMapOf()
        var appliedFilters : HashMap<String, Filter> = hashMapOf()
    }

    enum class FilterType{
        DEFAULT,
        TYPE,
        DEPARTMENT
    }

    fun getOffices() {
        _getOfficesUseCase().onEach { result ->
            clearOffices()
            when(result) {
                is Resource.Loading -> {
                    showLoading()
                    _uiState.value = _uiState.value?.apply {
                        this.isLoading = false
                    }
                }
                is Resource.Success -> {
                    hideLoading()
                    val offices = result.data ?: emptyList()
                    _uiState.value = _uiState.value?.apply {
                        this.offices = ArrayList(offices)
                        this.filteredOffices = ArrayList(offices)
                    }
                    filterOffices()
                }
                is Resource.Error -> {
                    hideLoading()
                    result.message?.let {
                        showError(RemoteExceptions.DEFAULT)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun filterOffices(){
        val offices = uiState.value?.offices
        val appliedFilters = uiState.value?.appliedFilters
        val filteredOffices = arrayListOf<Office>()
        offices?.forEach { office->
            appliedFilters?.let {
                if(it.size > 0){
                    var filterMatchFound = false
                    it.forEach {
                        when(it.value.category){
                            FilterType.TYPE.name.lowercase()->{
                                if(it.value.value.equals(office.type)){
                                    if(filterMatchFound.not()){
                                        filteredOffices.add(office)
                                    }
                                    filterMatchFound = true
                                }
                            }
                            FilterType.DEPARTMENT.name.lowercase()->{
                                if(it.value.value.equals(office.department)){
                                    if(filterMatchFound.not()){
                                        filteredOffices.add(office)
                                    }
                                    filterMatchFound = true
                                }
                            }
                        }
                    }
                }
            }
        }
        appliedFilters?.let {
            if(appliedFilters.size > 0){
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

    private fun clearOffices(){
        _uiState.value?.apply {
            offices?.clear()
        }
    }

    fun proceedWithUiAction(action : NavFlow){
        viewModelScope.launch {
            _uiState.value?.let {
                _uiActions.emit(Pair(action, it))
            }
        }
    }

    fun getFilters(){
        _getOfficeFiltersDatabaseUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    val filters = result.data ?: emptyList()
                    val filtersMap = hashMapOf<String, OfficeFilters>()
                    filters.forEach {
                        filtersMap.put(it.category, it)
                    }
                    _uiState.value = _uiState.value?.apply {
                        this.filtersMap = filtersMap
                        shouldUpdateFilters = true
                    }
                }
                else->{}
            }
        }.launchIn(viewModelScope)
    }

    fun addFilter(filter : Filter){
        _uiState.value = _uiState.value?.apply {
            this.filtersToApply[filter.category] = filter
            shouldUpdateFilters = false
        }
    }

    fun applyFilters(){
        _uiState.value?.filtersToApply?.let {
            _uiState.value?.appliedFilters?.clear()
            _uiState.value?.appliedFilters?.putAll(it)
        }
        viewModelScope.launch {
            _uiState.value?.let {
                _uiActions.emit(Pair(NavFlow.APPLY_FILTERS, it))
            }
        }
    }

    fun clearFilters(){
        _uiState.value = _uiState.value.apply {
            _uiState.value?.filteredOffices?.clear()
            _uiState.value?.offices?.let {
                _uiState.value?.filteredOffices?.addAll(it)
            }
            _uiState.value?.filtersToApply?.clear()
            _uiState.value?.appliedFilters?.clear()
        }
        viewModelScope.launch {
            _uiState.value?.let {
                _uiActions.emit(Pair(NavFlow.CLEAR_FILTERS, it))
            }
        }
    }
}