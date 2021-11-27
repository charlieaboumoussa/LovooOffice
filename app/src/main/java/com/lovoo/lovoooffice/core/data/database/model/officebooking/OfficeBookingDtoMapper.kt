package com.lovoo.lovoooffice.core.data.database.model.officebooking

import com.lovoo.lovoooffice.core.domain.model.OfficeBooking
import com.lovoo.lovoooffice.core.domain.util.DomainMapper
import java.util.concurrent.TimeUnit

class OfficeBookingDtoMapper : DomainMapper<OfficeBookingDto, OfficeBooking> {
    override fun mapToDomainModel(model: OfficeBookingDto): OfficeBooking {
        return OfficeBooking().apply {
            this.id = model.id
            this.officeId = model.officeId
            this.bookingReason = model.bookingReason
            this.bookingDate = model.bookingDate
            this.bookingPeriod = model.bookingPeriod
            val hours = TimeUnit.MILLISECONDS.toHours(model.bookingPeriod) % 24
            val minutes = TimeUnit.MILLISECONDS.toMinutes(model.bookingPeriod) % 60
            this.displayBookingPeriod = String.format("%d hr%s, %d min%s",
                hours,
                if(hours > 1){
                    "s"
                }else{
                    ""
                },
                minutes,
                if(minutes > 1){
                    "s"
                }else{
                    ""
                }
            )
        }
    }

    override fun mapFromDomainModel(domainModel: OfficeBooking): OfficeBookingDto {
        return OfficeBookingDto().apply {
            this.id = domainModel.id
            this.officeId = domainModel.officeId
            this.bookingDate = domainModel.bookingDate
            this.bookingPeriod = domainModel.bookingPeriod
        }
    }
}