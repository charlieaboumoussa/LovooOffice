package com.lovoo.lovoooffice.di

import com.example.latestmovies.model.repositories.OfficeRepositoryImpl
import com.lovoo.lovoooffice.core.data.database.dao.OfficeDao
import com.lovoo.lovoooffice.core.data.database.model.office.OfficeDtoMapper
import com.lovoo.lovoooffice.core.data.remote.OfficeService
import com.lovoo.lovoooffice.core.domain.repositories.OfficeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideOfficeRepository(
        officeService: OfficeService,
        officeDao: OfficeDao
    ): OfficeRepository{
        return OfficeRepositoryImpl(officeService, officeDao)
    }
}