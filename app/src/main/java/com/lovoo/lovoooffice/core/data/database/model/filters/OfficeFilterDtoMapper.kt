package com.lovoo.lovoooffice.core.data.database.model.officebooking

import com.lovoo.lovoooffice.core.domain.util.DomainMapper

class OfficeFilterDtoMapper : DomainMapper<OfficeFilterDto, OfficeFilters> {
    override fun mapToDomainModel(model: OfficeFilterDto): OfficeFilters {
        return OfficeFilters().apply {
            this.id = model.id
            this.values = model.values
            this.category = model.category
        }
    }

    override fun mapFromDomainModel(domainModel: OfficeFilters): OfficeFilterDto {
        return OfficeFilterDto().apply {
            this.id = domainModel.id
            this.values = domainModel.values
            this.category = domainModel.category
        }
    }
}