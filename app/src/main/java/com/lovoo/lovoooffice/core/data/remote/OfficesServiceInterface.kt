package com.lovoo.lovoooffice.core.data.remote

import com.lovoo.lovoooffice.core.data.database.dao.movie.Office
import retrofit2.http.GET

interface OfficesServiceInterface {

    @GET("lovooOffice")
    suspend fun getLovooOffices(): List<Office>
}