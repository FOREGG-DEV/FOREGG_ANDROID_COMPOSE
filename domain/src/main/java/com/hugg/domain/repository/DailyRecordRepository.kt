package com.hugg.domain.repository

import com.hugg.domain.base.ApiState
import com.hugg.domain.model.request.dailyRecord.CreateDailyRecordRequestVo
import com.hugg.domain.model.request.dailyRecord.CreateSideEffectRequestVo
import com.hugg.domain.model.request.dailyRecord.EditDailyRecordRequestVo
import com.hugg.domain.model.request.dailyRecord.PutEmotionVo
import com.hugg.domain.model.request.dailyRecord.InjectionAlarmRequestVo
import com.hugg.domain.model.response.calendar.SideEffectListItemVo
import com.hugg.domain.model.response.daily.DailyRecordResponseVo
import com.hugg.domain.model.response.daily.InjectionInfoResponseVo
import kotlinx.coroutines.flow.Flow

interface DailyRecordRepository {
    suspend fun getDailyRecord(): Flow<ApiState<DailyRecordResponseVo>>
    suspend fun createDailyRecord(request: CreateDailyRecordRequestVo): Flow<ApiState<Unit>>
    suspend fun createSideEffect(request: CreateSideEffectRequestVo): Flow<ApiState<Unit>>
    suspend fun getSideEffect(): Flow<ApiState<List<SideEffectListItemVo>>>
    suspend fun putEmotion(request: PutEmotionVo): Flow<ApiState<Unit>>
    suspend fun postShareInjection(request : InjectionAlarmRequestVo) : Flow<ApiState<Unit>>
    suspend fun getInjectionInfo(request : InjectionAlarmRequestVo) : Flow<ApiState<InjectionInfoResponseVo>>
    suspend fun deleteDailyRecord(id: Long): Flow<ApiState<Unit>>
    suspend fun editDailyRecord(request: EditDailyRecordRequestVo): Flow<ApiState<Unit>>
}