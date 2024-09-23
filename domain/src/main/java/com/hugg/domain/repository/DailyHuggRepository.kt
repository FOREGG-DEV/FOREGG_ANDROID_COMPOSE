package com.hugg.domain.repository

import com.hugg.domain.base.ApiState
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface DailyHuggRepository {
    suspend fun createDailyHugg(image: MultipartBody.Part, dto: RequestBody): Flow<ApiState<Unit>>
}