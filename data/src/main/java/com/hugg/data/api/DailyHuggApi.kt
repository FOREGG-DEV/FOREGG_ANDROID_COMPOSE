package com.hugg.data.api

import com.hugg.data.base.ApiResponse
import com.hugg.domain.base.ApiState
import com.hugg.domain.model.request.dailyHugg.CreateDailyHuggVo
import com.hugg.domain.model.vo.dailyHugg.CreateDailyHuggDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface DailyHuggApi {
    @Multipart
    @POST(Endpoints.DailyHugg.WRITE)
    suspend fun createDailyHugg(
        @Part image: MultipartBody.Part,
        @Part("dto") dto: RequestBody
    ): Response<ApiResponse<Unit>>
}