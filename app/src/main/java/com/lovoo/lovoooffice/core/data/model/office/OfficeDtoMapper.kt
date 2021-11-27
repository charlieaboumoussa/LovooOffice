package com.lovoo.lovoooffice.core.data.model.office

import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeBookingDtoMapper
import com.lovoo.lovoooffice.core.data.model.lovoofact.LovooFactDtoMapper
import com.lovoo.lovoooffice.core.domain.model.Office
import com.lovoo.lovoooffice.core.domain.util.DomainMapper
import javax.inject.Inject

class OfficeDtoMapper @Inject constructor(
    private val _lovooFactDtoMapper : LovooFactDtoMapper,
    private val _officeBookingDtoMapper : OfficeBookingDtoMapper,
    ) : DomainMapper<OfficeDto, Office> {
    override fun mapToDomainModel(model: OfficeDto): Office {
        return Office().apply {
            this.id = model.id
            this.name = model.name
            this.department = model.department
            this.type = model.type
            this.roomNumber = model.roomNumber
            this.officeLevel = model.officeLevel
            model.lovooFact?.let {
                this.lovooFact = _lovooFactDtoMapper.mapToDomainModel(it)
            }
            model.bookings?.let {
                this.bookings = ArrayList(
                    it.map {
                        _officeBookingDtoMapper.mapToDomainModel(it)
                    })
            }
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
            domainModel.lovooFact?.let {
                this.lovooFact = _lovooFactDtoMapper.mapFromDomainModel(it)
            }
            domainModel.bookings?.let {
                this.bookings = ArrayList(
                    it.map {
                        _officeBookingDtoMapper.mapFromDomainModel(it)
                    })
            }
        }
    }
}