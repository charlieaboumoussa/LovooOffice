package com.lovoo.lovoooffice.core.domain.repositories

import com.lovoo.lovoooffice.core.data.database.dao.movie.Office

interface OfficeRepositoryInterface {
    suspend fun getOffices(): List<Office>
}