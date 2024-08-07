package com.hugg.app.module

import com.hugg.data.repository.*
import com.hugg.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun providesAuthRepository(repositoryImpl: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    abstract fun providesForeggJwtRepository(repositoryImpl: ForeggJwtRepositoryImpl): ForeggJwtRepository

    @Singleton
    @Binds
    abstract fun providesScheduleRepository(repositoryImpl: ScheduleRepositoryImpl): ScheduleRepository

    @Singleton
    @Binds
    abstract fun providesHomeRepository(repositoryImpl: HomeRepositoryImpl) : HomeRepository

    @Singleton
    @Binds
    abstract fun providesChallengeRepository(repositoryImpl: ChallengeRepositoryImpl) : ChallengeRepository

    @Singleton
    @Binds
    abstract fun providesAccountRepository(repositoryImpl: AccountRepositoryImpl): AccountRepository

    @Singleton
    @Binds
    abstract fun providesProfileRepository(repositoryImpl: ProfileRepositoryImpl): ProfileRepository

    @Singleton
    @Binds
    abstract fun providesDailyRecordRepository(repositoryImpl: DailyRecordRepositoryImpl): DailyRecordRepository

    @Singleton
    @Binds
    abstract fun providesInformationRepository(repositoryImpl: InformationRepositoryImpl): InformationRepository
}