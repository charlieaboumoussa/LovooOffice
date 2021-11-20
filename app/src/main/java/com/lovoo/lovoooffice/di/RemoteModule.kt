package com.lovoo.lovoooffice.di

import android.content.Context
import com.lovoo.lovoooffice.BuildConfig
import com.lovoo.lovoooffice.core.data.database.model.office.OfficeDtoMapper
import com.lovoo.lovoooffice.core.data.remote.OfficeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Singleton
    @Provides
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {

        val httpCacheDirectory = File(context.cacheDir.absolutePath, "HttpCache")
        val cache = Cache(httpCacheDirectory, BuildConfig.CACHE_SIZE_BYTES)

        val headerInterceptor = Interceptor {
            val requestBuilder = it.request().newBuilder()
            //here you can add headers
            requestBuilder.addHeader("Authorization", BuildConfig.BASIC_TOKEN)
            it.proceed(requestBuilder.build())
        }

        val client = OkHttpClient.Builder()
            .connectTimeout(BuildConfig.CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(BuildConfig.READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(BuildConfig.WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .cache(cache)
            .addInterceptor(headerInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideOfficeService(
        retrofit: Retrofit
    ): OfficeService{
        return retrofit.create(OfficeService::class.java)
    }

    @Singleton
    @Provides
    fun provideOfficeDtoMapper(): OfficeDtoMapper{
        return OfficeDtoMapper()
    }
}