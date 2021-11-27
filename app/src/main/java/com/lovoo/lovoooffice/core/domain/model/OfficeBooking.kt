package com.lovoo.lovoooffice.core.domain.model

import java.io.Serializable

data class OfficeBooking(
    var id: Int = 0,
    var officeId : String = "",
    var bookingReason : String = "",
    var bookingDate : Long = 0L,
    var bookingPeriod : Long = 0L,
    var displayBookingPeriod : String? = null,
): Serializable