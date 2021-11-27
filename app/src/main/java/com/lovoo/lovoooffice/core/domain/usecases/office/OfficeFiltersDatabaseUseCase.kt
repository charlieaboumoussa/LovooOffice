package com.lovoo.lovoooffice.core.domain.usecases.office

import com.lovoo.lovoooffice.common.base.state.Resource
import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeFilterDto
import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeFilterDtoMapper
import com.lovoo.lovoooffice.core.domain.repositories.OfficeRepository
import com.lovoo.lovoooffice.core.domain.usecases.BaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class OfficeFiltersDatabaseUseCase @Inject constructor(
    private val _repository: OfficeRepository
): BaseUseCase<Boolean>(){

    override fun useCaseName(): String = "OfficeFiltersDatabaseUseCase"

    operator fun invoke(): Flow<Resource<Boolean>> = baseFlow {baseFlow->
        _repository.getOfficeFilters(object : OfficeRepository.IGetOfficeFilters{
            override fun onGetFiltersSuccess(filters: List<OfficeFilterDto>) {
                baseFlow{
                    _repository.insertFilters(filters)
                    baseFlow.emit(Resource.Success(true))
                }
            }

            override fun onGetFiltersFailed(errorMessage: String) {
                baseFlow {
                    baseFlow.emit(Resource.Error(errorMessage))
                }
            }
        })
    }



}
