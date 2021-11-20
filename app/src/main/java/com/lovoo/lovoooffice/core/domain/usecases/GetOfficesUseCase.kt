package com.lovoo.lovoooffice.core.domain.usecases

import com.lovoo.lovoooffice.common.base.state.Resource
import com.lovoo.lovoooffice.core.data.database.model.office.OfficeDtoMapper
import com.lovoo.lovoooffice.core.domain.model.Office
import com.lovoo.lovoooffice.core.domain.repositories.OfficeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOfficesUseCase @Inject constructor(
    private val _repository: OfficeRepository,
    private val _mapper: OfficeDtoMapper
): BaseUseCase<List<Office>>() {

    override fun useCaseName(): String = "GetProductsUseCase"

    operator fun invoke(): Flow<Resource<List<Office>>> = baseFlow {
        it.emit(Resource.Loading())
        val offices = _repository.getOffices().map {
            _mapper.mapToDomainModel(it)
        }
        it.emit(Resource.Success(offices))
    }

}
