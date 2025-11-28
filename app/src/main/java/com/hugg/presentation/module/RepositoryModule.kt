package com.hugg.presentation.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.hugg.data.repository.*
import com.hugg.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "foregg_data_store"
)

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
    abstract fun providesHomeRepository(repositoryImpl: HomeRepositoryImpl): HomeRepository

    @Singleton
    @Binds
    abstract fun providesChallengeRepository(repositoryImpl: ChallengeRepositoryImpl): ChallengeRepository

    @Singleton
    @Binds
    abstract fun providesAccountRepository(repositoryImpl: AccountRepositoryImpl): AccountRepository

    @Singleton
    @Binds
    abstract fun providesProfileRepository(repositoryImpl: ProfileRepositoryImpl): ProfileRepository

    @Singleton
    @Binds
    abstract fun providesDailyHuggRepository(repositoryImpl: DailyHuggRepositoryImpl): DailyHuggRepository

    @Singleton
    @Binds
    abstract fun providesDailyRecordRepository(repositoryImpl: DailyRecordRepositoryImpl): DailyRecordRepository
}

@Module
@InstallIn(SingletonComponent::class)
object ForeggDataStoreModule {

    @Provides
    @Singleton
    fun provideForeggDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.tokenDataStore
}