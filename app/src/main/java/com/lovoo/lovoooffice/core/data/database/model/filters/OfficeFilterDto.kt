package com.lovoo.lovoooffice.core.data.database.model.officebooking

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "OFFICE_FILTER")
class OfficeFilterDto {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "CATEGORY")
    var category: String = ""

    @ColumnInfo(name = "VALUES")
    var values: ArrayList<String>? = null
}