package com.lovoo.lovoooffice.core.data.database.model.officebooking

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "OFFICE_BOOKING")
class OfficeBookingDto {

    @PrimaryKey(autoGenerate = true)
    var id: Int = -1

    @ColumnInfo(name = "OFFICE_ID")
    var officeId: String = ""

    @ColumnInfo(name = "BOOKING_REASON")
    var bookingReason: String = ""

    @ColumnInfo(name = "BOOKING_DATE")
    var bookingDate: Long = 0L

    @ColumnInfo(name = "BOOKING_PERIOD")
    var bookingPeriod: Long = 0L
}