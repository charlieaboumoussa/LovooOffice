package com.lovoo.lovoooffice.core.data.database.dao.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "OFFICE")
class Office {

    @PrimaryKey
    @SerializedName("id")
    var id: Int = 0

    @ColumnInfo(name = "NAME")
    @SerializedName("name")
    var name = ""

    @ColumnInfo(name = "DEPARTMENT")
    @SerializedName("department")
    var department = ""

    @ColumnInfo(name = "TYPE")
    @SerializedName("type")
    var type = ""

    @ColumnInfo(name = "ROOM_NUMBER")
    @SerializedName("roomNumber")
    var roomNumber = ""

    @ColumnInfo(name = "OFFICE_LEVEL")
    @SerializedName("officeLevel")
    var officeLevel = ""
}