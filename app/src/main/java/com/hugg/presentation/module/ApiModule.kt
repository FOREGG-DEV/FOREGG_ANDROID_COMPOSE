package com.hugg.presentation.module

import com.hugg.data.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun providePlubJwtTokenApi(@NormalRetrofit retrofit: Retrofit): ForeggJwtTokenApi {
        return retrofit.create(ForeggJwtTokenApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthTokenApi(@NormalRetrofit retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun provideFcmApi(@AuthRetrofit retrofit: Retrofit): FcmApi {
        return retrofit.create(FcmApi::class.java)
    }

    @Singleton
    @Provides
    fun provideScheduleApi(@AuthRetrofit retrofit: Retrofit): ScheduleApi {
        return retrofit.create(ScheduleApi::class.java)
    }

    @Singleton
    @Provides
    fun provideHomeApi(@AuthRetrofit retrofit: Retrofit) : HomeApi {
        return retrofit.create(HomeApi::class.java)
    }

    @Singleton
    @Provides
    fun provideChallengeApi(@AuthRetrofit retrofit: Retrofit) : ChallengeApi {
        return retrofit.create(ChallengeApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAccountApi(@AuthRetrofit retrofit: Retrofit): AccountApi {
        return retrofit.create(AccountApi::class.java)
    }

    @Singleton
    @Provides
    fun provideProfileApi(@AuthRetrofit retrofit: Retrofit): ProfileApi {
        return retrofit.create(ProfileApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDailyHuggApi(@AuthRetrofit retrofit: Retrofit): DailyHuggApi {
        return retrofit.create(DailyHuggApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDailyRecordApi(@AuthRetrofit retrofit: Retrofit): DailyRecordApi {
        return retrofit.create(DailyRecordApi::class.java)
    }

    @Singleton
    @Provides
    fun provideInformationApi(@AuthRetrofit retrofit: Retrofit): InformationApi {
        return retrofit.create(InformationApi::class.java)
    }
}