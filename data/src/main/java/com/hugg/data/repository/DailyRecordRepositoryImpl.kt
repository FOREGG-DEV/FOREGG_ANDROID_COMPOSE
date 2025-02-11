package com.hugg.data.repository

import com.hugg.data.api.DailyRecordApi
import com.hugg.data.base.BaseRepository
import com.hugg.data.mapper.UnitResponseMapper
import com.hugg.data.mapper.dailyRecord.DailyRecordResponseMapper
import com.hugg.data.mapper.dailyRecord.InjectionInfoResponseMapper
import com.hugg.data.mapper.dailyRecord.SideEffectResponseMapper
import com.hugg.domain.base.ApiState
import com.hugg.domain.model.request.dailyRecord.CreateDailyRecordRequestVo
import com.hugg.domain.model.request.dailyRecord.CreateSideEffectRequestVo
import com.hugg.domain.model.request.dailyRecord.EditDailyRecordRequestVo
import com.hugg.domain.model.request.dailyRecord.InjectionAlarmRequestVo
import com.hugg.domain.model.request.dailyRecord.PutEmotionVo
import com.hugg.domain.model.response.calendar.SideEffectListItemVo
import com.hugg.domain.model.response.daily.DailyRecordResponseVo
import com.hugg.domain.model.response.daily.InjectionInfoResponseVo
import com.hugg.domain.repository.DailyRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DailyRecordRepositoryImpl @Inject constructor(
    private val dailyRecordApi: DailyRecordApi
): DailyRecordRepository, BaseRepository() {
    override suspend fun postShareInjection(request : InjectionAlarmRequestVo): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { dailyRecordApi.shareInjection(request.id, request.date, request.time) }, UnitResponseMapper)
    }

    override suspend fun getInjectionInfo(request: InjectionAlarmRequestVo): Flow<ApiState<InjectionInfoResponseVo>> {
        return apiLaunch(apiCall = { dailyRecordApi.getInjectionInfo(request.id, request.type, request.date, request.time) }, InjectionInfoResponseMapper)
    }
}