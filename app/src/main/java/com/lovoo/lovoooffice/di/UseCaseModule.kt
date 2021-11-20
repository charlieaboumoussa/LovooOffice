package com.lovoo.lovoooffice.di

import com.lovoo.lovoooffice.core.data.database.model.office.OfficeDtoMapper
import com.lovoo.lovoooffice.core.domain.repositories.OfficeRepository
import com.lovoo.lovoooffice.core.domain.usecases.GetOfficesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideGetOfficesUseCase(
        officeRepository: OfficeRepository,
        officeDtoMapper: OfficeDtoMapper
    ): GetOfficesUseCase{
        return GetOfficesUseCase(officeRepository, officeDtoMapper)
    }
}