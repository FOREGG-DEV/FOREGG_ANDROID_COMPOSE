package com.hugg.domain.repository

import com.hugg.domain.base.ApiState
import com.hugg.domain.model.request.sign.SaveForeggJwtRequestVo
import com.hugg.domain.model.response.sign.ForeggJwtResponseVo
import kotlinx.coroutines.flow.Flow

interface ForeggJwtRepository {

    suspend fun saveAccessTokenAndRefreshToken(request: SaveForeggJwtRequestVo): Flow<Boolean>
    fun getAccessToken(): Flow<String>
    fun getRefreshToken(): Flow<String>
    fun setAlarmSetting(flag : Boolean): Flow<Boolean>
    fun getAlarmSetting(): Flow<Boolean>
    suspend fun reIssueToken(request : String): Flow<ApiState<ForeggJwtResponseVo>>
}