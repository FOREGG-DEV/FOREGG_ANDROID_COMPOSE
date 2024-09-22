package com.hugg.data.api

import com.hugg.domain.base.ApiState
import com.hugg.domain.model.request.dailyHugg.CreateDailyHuggVo
import retrofit2.http.Body
import retrofit2.http.POST

interface DailyHuggApi {
    @POST(Endpoints.DailyHugg.WRITE)
    suspend fun createDailyHugg(
        @Body request: CreateDailyHuggVo
    ): ApiState<Unit>
}