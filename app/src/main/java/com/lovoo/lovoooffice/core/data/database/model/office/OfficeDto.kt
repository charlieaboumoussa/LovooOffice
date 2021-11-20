package com.lovoo.lovoooffice.core.data.database.model.office

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "OFFICE")
class OfficeDto : Serializable {

    @PrimaryKey
    @SerializedName("id")
    var id: String = ""

    @ColumnInfo(name = "NAME")
    @SerializedName("name")
    var name: String? = ""

    @ColumnInfo(name = "DEPARTMENT")
    @SerializedName("department")
    var department: String? = ""

    @ColumnInfo(name = "TYPE")
    @SerializedName("type")
    var type: String? = ""

    @ColumnInfo(name = "ROOM_NUMBER")
    @SerializedName("roomNumber")
    var roomNumber: String? = ""

    @ColumnInfo(name = "OFFICE_LEVEL")
    @SerializedName("officeLevel")
    var officeLevel: Int? = 0
}