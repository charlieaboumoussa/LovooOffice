package com.lovoo.lovoooffice.core.data.model.office

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeBookingDto
import com.lovoo.lovoooffice.core.data.model.lovoofact.LovooFactDto

class OfficeDto {
    @SerializedName("id")
    var id: String = ""

    @SerializedName("name")
    var name: String? = ""

    @SerializedName("department")
    var department: String? = ""

    @SerializedName("type")
    var type: String? = ""

    @SerializedName("roomNumber")
    var roomNumber: String? = ""

    @SerializedName("officeLevel")
    var officeLevel: Int? = 0

    @SerializedName("lovooFact")
    var lovooFact: LovooFactDto? = null

    @Expose(serialize = false)
    var bookings: ArrayList<OfficeBookingDto>? = null
}