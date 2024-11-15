package com.hugg.domain.repository

import com.hugg.domain.base.ApiState
import com.hugg.domain.model.response.dailyHugg.DailyHuggItemVo
import com.hugg.domain.model.response.dailyHugg.DailyHuggListResponseVo
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface DailyHuggRepository {
    suspend fun createDailyHugg(image: MultipartBody.Part?, dto: RequestBody): Flow<ApiState<Unit>>
    suspend fun getDailyHuggByDate(date: String): Flow<ApiState<DailyHuggItemVo>>
    suspend fun editDailyHugg(id: Long, image: MultipartBody.Part, dto: RequestBody): Flow<ApiState<Unit>>
    suspend fun deleteDailyHugg(id: Long): Flow<ApiState<Unit>>
    suspend fun getDailyHuggList(page: Int): Flow<ApiState<DailyHuggListResponseVo>>
    suspend fun getDailyHuggSpecialQuestion() : Flow<ApiState<String>>
}