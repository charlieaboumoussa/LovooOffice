package com.lovoo.lovoooffice.core.data

import android.content.Context
import androidx.room.Room
import com.example.latestmovies.model.database.AppDatabase
import com.lovoo.lovoooffice.BuildConfig
import com.lovoo.lovoooffice.R
import com.lovoo.lovoooffice.core.data.database.dao.movie.OfficeDao
import com.lovoo.lovoooffice.core.data.remote.OfficesServiceInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {

    private const val DATABASE_NAME = "DATABASE"
    private var mDatabase : AppDatabase? = null
    private var mRetrofit : Retrofit? = null

    fun initializeDatabase(context: Context) {
        if(mDatabase == null) {
            mDatabase = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
//                .addMigrations()
                .build()
        }
    }

    fun initializeRetrofit(context: Context){
        mRetrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(
                OkHttpClient.Builder()
//                    .addInterceptor { chain ->
//                        val url = chain
//                            .request()
//                            .url()
//                            .newBuilder()
//                            .addQueryParameter("api_key", context.getString(R.string.api_key))
//                            .build()
//                        chain.proceed(chain.request().newBuilder().url(url).build())
//                    }
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getRetrofit() : Retrofit?{
        return mRetrofit
    }

    fun getOfficeDao() : OfficeDao?{
        return mDatabase?.officeDao()
    }

    fun getOfficesService() : OfficesServiceInterface?{
        return mRetrofit?.create(OfficesServiceInterface::class.java)
    }
}