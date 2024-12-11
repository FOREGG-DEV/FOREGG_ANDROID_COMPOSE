package com.hugg.data.api

import com.hugg.data.base.ApiResponse
import com.hugg.data.dto.dailyRecord.DailyRecordResponse
import com.hugg.data.dto.dailyRecord.InjectionInfoResponse
import com.hugg.data.dto.dailyRecord.SideEffectResponseListItem
import com.hugg.domain.model.request.dailyRecord.CreateDailyRecordRequestVo
import com.hugg.domain.model.request.dailyRecord.CreateSideEffectRequestVo
import com.hugg.domain.model.request.dailyRecord.EmotionVo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface DailyRecordApi {

    companion object{
        const val PATH_ID = "id"
        const val QUERY_TIME = "time"
    }

    @POST(Endpoints.DailyRecord.SHARE_INJECTION)
    suspend fun shareInjection(
        @Path(PATH_ID) id : Long,
        @Query(QUERY_TIME) time : String,
    ): Response<ApiResponse<Unit>>
    @GET(Endpoints.DailyRecord.GET_INJECTION_INFO)
    suspend fun getInjectionInfo(
        @Path(PATH_ID) id : Long,
        @Query(QUERY_TIME) time : String,
    ): Response<ApiResponse<InjectionInfoResponse>>
}