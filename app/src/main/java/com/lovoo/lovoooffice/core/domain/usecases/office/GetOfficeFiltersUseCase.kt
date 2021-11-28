package com.lovoo.lovoooffice.core.domain.usecases.office

import com.lovoo.lovoooffice.common.base.state.Resource
import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeFilterDto
import com.lovoo.lovoooffice.core.domain.repositories.OfficeRepository
import com.lovoo.lovoooffice.core.domain.usecases.BaseUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetOfficeFiltersUseCase @Inject constructor(
    private val _repository: OfficeRepository
): BaseUseCase<Boolean>(){

    override fun useCaseName(): String = "GetOfficeFiltersUseCase"

    operator fun invoke(): Flow<Resource<Boolean>> = baseFlow { flow->
        flow.emit(Resource.Loading())
        val filters = _repository.getRemoteOfficeFilters()
        _repository.insertFilters(filters)
        flow.emit(Resource.Success(true))
    }



}
