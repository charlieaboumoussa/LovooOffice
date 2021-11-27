package com.lovoo.lovoooffice.core.data.database.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lovoo.lovoooffice.core.data.model.lovoofact.LovooFactDto
import java.lang.reflect.Type

object LovooFactConverter {
    @TypeConverter
    fun fromString(value: String?): LovooFactDto? {
        val listType: Type = object : TypeToken<LovooFactDto?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromObject(lovooFact: LovooFactDto?): String {
        val lovooFactType: Type = object : TypeToken<LovooFactDto?>() {}.type
        return Gson().toJson(lovooFact, lovooFactType)
    }
}