package com.example.latestmovies.model.repositories

import com.google.gson.JsonElement
import com.lovoo.lovoooffice.core.data.database.dao.OfficeBookingDao
import com.lovoo.lovoooffice.core.data.database.dao.OfficeFilterDao
import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeBookingDto
import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeFilterDto
import com.lovoo.lovoooffice.core.data.model.office.OfficeDto
import com.lovoo.lovoooffice.core.data.remote.OfficeService
import com.lovoo.lovoooffice.core.domain.repositories.OfficeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class OfficeRepositoryImpl @Inject constructor(
    private val _officesService: OfficeService,
    private val _officeBookingDao: OfficeBookingDao,
    private val _officeFilterDao: OfficeFilterDao
) : OfficeRepository {

    override suspend fun getOffices() : List<OfficeDto>{
        return _officesService.getLovooOffices()
    }

    override suspend fun getOfficeBookings(): List<OfficeBookingDto> {
        return _officeBookingDao.getOfficeBookings()
    }

    override suspend fun getOfficeFilters(getOfficeFiltersInterface: OfficeRepository.IGetOfficeFilters) {
        _officesService.getLovooOfficesFilters().enqueue(object : Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                val jsonObject = response.body()?.asJsonObject
                val parametersJsonObject = jsonObject?.getAsJsonObject("GET")?.getAsJsonObject("parameters")
                val filters = ArrayList<OfficeFilterDto>()
                parametersJsonObject?.let {
                    for ((key, value) in parametersJsonObject.entrySet()) {
                        val filter = OfficeFilterDto()
                        filter.category = key
                        for (filterValue in value.asJsonObject.getAsJsonArray("values")){
                            if(filter.values == null){
                                filter.values = ArrayList()
                            }
                            filter.values?.add(filterValue.asString)
                        }
                        filters.add(filter)
                    }
                }
                GlobalScope.launch(Dispatchers.IO) {
                    _officeFilterDao.insert(filters.toList())
                }
                getOfficeFiltersInterface.onGetFiltersSuccess(filters)
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                getOfficeFiltersInterface.onGetFiltersFailed(t.localizedMessage)
            }
        })
    }

    override fun flowOfficeBookings(): Flow<List<OfficeBookingDto>> {
        return _officeBookingDao.flowOfficeBookings()
    }

    override fun flowOfficeFilters(): Flow<List<OfficeFilterDto>> {
        return _officeFilterDao.flowOfficeFilters()
    }

    override suspend fun deleteOfficeBooking(officeId: String) {
        _officeBookingDao.deleteBookingById(officeId)
    }

    override suspend fun insertOfficeBooking(officeBookingDto: OfficeBookingDto) {
        _officeBookingDao.insert(officeBookingDto)
    }

    override suspend fun insertFilters(filters: List<OfficeFilterDto>) {
        _officeFilterDao.insert(filters)
    }

}