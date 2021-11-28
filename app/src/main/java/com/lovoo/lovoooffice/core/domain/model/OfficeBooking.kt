package com.lovoo.lovoooffice.core.domain.model

import java.io.Serializable

data class OfficeBooking(
    var id: Int = 0,
    var officeId : String = "",
    var reason : String = "",
    var startDate : Long = 0L,
    var startTime : Long = 0L,
    var endTime : Long = 0L,
    var displayBookingPeriod : String? = null,
): Serializable