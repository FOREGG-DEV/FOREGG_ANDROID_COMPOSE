package com.hugg.domain.repository

import com.hugg.domain.base.ApiState
import com.hugg.domain.model.request.calendar.AddMedicalRecordRequest
import com.hugg.domain.model.request.calendar.ScheduleDetailRequestVo
import com.hugg.domain.model.vo.calendar.MedicalRecord
import com.hugg.domain.model.vo.calendar.ScheduleDetailVo
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    suspend fun getScheduleList(request : String) : Flow<ApiState<List<ScheduleDetailVo>>>
    suspend fun deleteSchedule(request: Long) : Flow<ApiState<Unit>>
    suspend fun getDetailRecord(request : Long) : Flow<ApiState<ScheduleDetailVo>>
    suspend fun addSchedule(request : ScheduleDetailRequestVo) : Flow<ApiState<Unit>>
    suspend fun modifySchedule(id : Long, request : ScheduleDetailRequestVo) : Flow<ApiState<Unit>>
    suspend fun addMedicalRecord(id : Long, request : AddMedicalRecordRequest) : Flow<ApiState<Unit>>
    suspend fun getMedicalRecordAndSideEffect(id : Long) : Flow<ApiState<MedicalRecord>>
}