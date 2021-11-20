package com.lovoo.lovoooffice.core.domain.repositories

import com.lovoo.lovoooffice.core.data.database.model.office.OfficeDto

interface OfficeRepository {
    suspend fun getOffices(): List<OfficeDto>
}