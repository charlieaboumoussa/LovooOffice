package com.lovoo.lovoooffice.core.data.remote

import com.google.gson.JsonElement
import com.lovoo.lovoooffice.core.data.model.office.OfficeDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.OPTIONS

interface OfficeService {

    @GET("lovooOffice")
    suspend fun getLovooOffices(): List<OfficeDto>

    @OPTIONS("lovooOffice")
    suspend fun getLovooOfficesFilters(): Call<JsonElement>
}