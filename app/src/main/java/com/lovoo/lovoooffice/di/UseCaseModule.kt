package com.lovoo.lovoooffice.di

import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeBookingDtoMapper
import com.lovoo.lovoooffice.core.data.database.model.officebooking.OfficeFilterDtoMapper
import com.lovoo.lovoooffice.core.data.model.office.OfficeDtoMapper
import com.lovoo.lovoooffice.core.domain.repositories.OfficeRepository
import com.lovoo.lovoooffice.core.domain.usecases.office.*
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
    ): GetOfficesUseCase {
        return GetOfficesUseCase(officeRepository, officeDtoMapper)
    }

    @Singleton
    @Provides
    fun provideOfficeBookingsDatabaseUseCase(
        officeRepository: OfficeRepository,
        officeBookingDtoMapper: OfficeBookingDtoMapper
    ): OfficeBookingsDatabaseUseCase {
        return OfficeBookingsDatabaseUseCase(officeRepository, officeBookingDtoMapper)
    }

    @Singleton
    @Provides
    fun provideGetOfficeBookingsDatabaseUseCase(
        officeRepository: OfficeRepository,
        officeBookingDtoMapper: OfficeBookingDtoMapper
    ): GetOfficeBookingsDatabaseUseCase {
        return GetOfficeBookingsDatabaseUseCase(officeRepository, officeBookingDtoMapper)
    }

    @Singleton
    @Provides
    fun provideGetOfficeFiltersUseCase(
        officeRepository: OfficeRepository
    ): GetOfficeFiltersUseCase {
        return GetOfficeFiltersUseCase(officeRepository)
    }

    @Singleton
    @Provides
    fun provideGetOfficeFiltersDatabaseUseCase(
        officeRepository: OfficeRepository,
        officeFilterDtoMapper: OfficeFilterDtoMapper
    ): GetOfficeFiltersDatabaseUseCase {
        return GetOfficeFiltersDatabaseUseCase(officeRepository, officeFilterDtoMapper)
    }

}