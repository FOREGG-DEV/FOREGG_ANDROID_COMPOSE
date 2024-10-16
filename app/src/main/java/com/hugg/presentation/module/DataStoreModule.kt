package com.hugg.presentation.module

import android.content.Context
import com.hugg.data.datastore.HuggDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideHuggDataStore(@ApplicationContext context: Context): HuggDataStore {
        return HuggDataStore(context)
    }
}