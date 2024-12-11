package com.hugg.data.repository

import com.hugg.data.api.HomeApi
import com.hugg.data.base.BaseRepository
import com.hugg.data.mapper.home.HomeResponseMapper
import com.hugg.data.mapper.notification.NotificationHistoryResponseMapper
import com.hugg.domain.base.ApiState
import com.hugg.domain.model.response.home.HomeResponseVo
import com.hugg.domain.model.response.notification.NotificationHistoryResponseVo
import com.hugg.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi
) : HomeRepository, BaseRepository() {
    override suspend fun getHome(): Flow<ApiState<HomeResponseVo>> {
        return apiLaunch(apiCall = { homeApi.getHome() }, HomeResponseMapper)
    }

    override suspend fun getNotificationHistory(page: Long): Flow<ApiState<NotificationHistoryResponseVo>> {
        return apiLaunch(apiCall = { homeApi.getNotificationHistory(page) }, NotificationHistoryResponseMapper)
    }
}