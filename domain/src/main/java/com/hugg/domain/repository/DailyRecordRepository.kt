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
    suspend fun postShareInjection(request : InjectionAlarmRequestVo) : Flow<ApiState<Unit>>
    suspend fun getInjectionInfo(request : InjectionAlarmRequestVo) : Flow<ApiState<InjectionInfoResponseVo>>
}