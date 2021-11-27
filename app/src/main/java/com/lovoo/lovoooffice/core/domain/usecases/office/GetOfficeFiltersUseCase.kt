package com.lovoo.lovoooffice.core.domain.usecases.office

import com.lovoo.lovoooffice.common.base.state.Resource
import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeFilters
import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeFilterDtoMapper
import com.lovoo.lovoooffice.core.domain.repositories.OfficeRepository
import com.lovoo.lovoooffice.core.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class GetOfficeFiltersUseCase @Inject constructor(
    private val _repository: OfficeRepository,
    private val _officeFilterDtoMapper: OfficeFilterDtoMapper
): BaseUseCase<List<OfficeFilters>>(){

    override fun useCaseName(): String = "GetOfficeFiltersUseCase"

    operator fun invoke(): Flow<Resource<List<OfficeFilters>>> = baseFlow { baseFlow->
        baseFlow.emit(Resource.Loading())
        _repository.flowOfficeFilters().collect {
            val officeFilters = it.map { officeFilter->
                _officeFilterDtoMapper.mapToDomainModel(officeFilter)
            }
            baseFlow.emit(Resource.Success(officeFilters))
        }
    }



}
