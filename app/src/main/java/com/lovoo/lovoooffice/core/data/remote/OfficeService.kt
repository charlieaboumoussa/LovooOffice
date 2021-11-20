package com.lovoo.lovoooffice.core.data.remote

import com.lovoo.lovoooffice.core.data.database.model.office.OfficeDto
import retrofit2.http.GET

interface OfficeService {

    @GET("lovooOffice")
    suspend fun getLovooOffices(): List<OfficeDto>
}