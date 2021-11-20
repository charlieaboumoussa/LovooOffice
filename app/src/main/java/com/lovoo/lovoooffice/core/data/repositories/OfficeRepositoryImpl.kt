package com.example.latestmovies.model.repositories

import com.lovoo.lovoooffice.core.data.database.model.office.OfficeDto
import com.lovoo.lovoooffice.core.data.database.dao.OfficeDao
import com.lovoo.lovoooffice.core.data.database.model.office.OfficeDtoMapper
import com.lovoo.lovoooffice.core.data.remote.OfficeService
import com.lovoo.lovoooffice.core.domain.repositories.OfficeRepository
import javax.inject.Inject

class OfficeRepositoryImpl @Inject constructor(
    private val _officesService: OfficeService,
    private val _officeDao: OfficeDao
) : OfficeRepository {

//    fun flowOffices(): Flow<List<Office>> {
//        return _officeDao.flowOffices()
//    }

    override suspend fun getOffices() : List<OfficeDto>{
        return _officesService.getLovooOffices()
//        _officesService.getLovooOffices().enqueue(object : Callback<List<Office>> {
//            override fun onResponse(call: Call<List<Office>>, response: Response<List<Office>>) {
//                response.body()?.let { offices ->
//                    mGetOfficesInterface?.getOfficesSuccess(offices)
//                }
//            }
//
//            override fun onFailure(call: Call<List<Office>>, t: Throwable) {
//                t.message?.let {
//                    mGetOfficesInterface?.getOfficesFailed(t.message!!)
//                }
//            }
//        })

    }

}