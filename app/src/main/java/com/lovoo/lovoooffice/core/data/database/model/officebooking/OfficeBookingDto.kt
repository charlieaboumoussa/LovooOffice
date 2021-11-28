package com.lovoo.lovoooffice.core.data.database.model.officebooking

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "OFFICE_BOOKING")
class OfficeBookingDto {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "OFFICE_ID")
    var officeId: String = ""

    @ColumnInfo(name = "REASON")
    var reason: String = ""

    @ColumnInfo(name = "START_DATE")
    var startDate: Long = 0L

    @ColumnInfo(name = "START_TIME")
    var startTime: Long = 0L

    @ColumnInfo(name = "END_TIME")
    var endTime: Long = 0L
}