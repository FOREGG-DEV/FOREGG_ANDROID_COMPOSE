package com.hugg.domain.repository

import com.hugg.domain.base.ApiState
import com.hugg.domain.model.response.home.HomeResponseVo
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getHome() : Flow<ApiState<HomeResponseVo>>
}