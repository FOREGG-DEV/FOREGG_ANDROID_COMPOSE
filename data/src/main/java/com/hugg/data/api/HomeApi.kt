package com.hugg.data.api

import com.hugg.data.base.ApiResponse
import com.hugg.data.dto.HomeResponse
import com.hugg.data.dto.notification.NotificationHistoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {
    companion object {
        const val QUERY_PAGE = "page"
    }

    @GET(Endpoints.Home.HOME)
    suspend fun getHome() : Response<ApiResponse<HomeResponse>>

    @GET(Endpoints.Notification.NOTIFICATION_HISTORY)
    suspend fun getNotificationHistory(
        @Query(QUERY_PAGE) page : Long
    ) : Response<ApiResponse<NotificationHistoryResponse>>
}