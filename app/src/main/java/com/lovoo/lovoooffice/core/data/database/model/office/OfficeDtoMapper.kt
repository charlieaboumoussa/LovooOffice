package com.lovoo.lovoooffice.core.data.database.model.office

import com.lovoo.lovoooffice.core.domain.model.Office
import com.lovoo.lovoooffice.core.domain.util.DomainMapper

class OfficeDtoMapper : DomainMapper<OfficeDto, Office> {
    override fun mapToDomainModel(model: OfficeDto): Office {
        return Office().apply {
            this.id = model.id
            this.name = model.name
            this.department = model.department
            this.type = model.type
            this.roomNumber = model.roomNumber
            this.officeLevel = model.officeLevel
        }
    }

    override fun mapFromDomainModel(domainModel: Office): OfficeDto {
        return OfficeDto().apply {
            this.id = domainModel.id
            this.name = domainModel.name
            this.department = domainModel.department
            this.type = domainModel.type
            this.roomNumber = domainModel.roomNumber
            this.officeLevel = domainModel.officeLevel
        }
    }
}