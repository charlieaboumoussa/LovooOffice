package com.example.latestmovies.model.repositories

import com.lovoo.lovoooffice.core.data.database.dao.movie.Office
import com.lovoo.lovoooffice.core.data.database.dao.movie.OfficeDao
import com.lovoo.lovoooffice.core.data.remote.OfficesServiceInterface
import com.lovoo.lovoooffice.core.domain.repositories.OfficeRepositoryInterface

class OfficeRepository(
    private val _officesService: OfficesServiceInterface,
    private val _officeDao: OfficeDao
) : OfficeRepositoryInterface {

//    private var mGetOfficesInterface : IGetOffices? = null
//
//    interface IGetOffices{
//        fun getOfficesSuccess(offices : List<Office>){}
//        fun getOfficesFailed(errorMessage : String){}
//    }

//    fun flowOffices(): Flow<List<Office>> {
//        return _officeDao.flowOffices()
//    }

    override suspend fun getOffices() : List<Office>{
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