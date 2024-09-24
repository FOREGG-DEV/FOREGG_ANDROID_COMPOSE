package com.hugg.data.api

import com.hugg.data.base.ApiResponse
import com.hugg.data.dto.dailyHugg.DailyHuggResponse
import com.hugg.domain.base.ApiState
import com.hugg.domain.model.request.dailyHugg.CreateDailyHuggVo
import com.hugg.domain.model.vo.dailyHugg.CreateDailyHuggDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface DailyHuggApi {

    companion object {
        const val DATE = "date"
    }

    @Multipart
    @POST(Endpoints.DailyHugg.WRITE)
    suspend fun createDailyHugg(
        @Part image: MultipartBody.Part,
        @Part("dto") dto: RequestBody
    ): Response<ApiResponse<Unit>>

    @GET(Endpoints.DailyHugg.BYDATE)
    suspend fun getDailyHuggByDate(
        @Path(DATE) date: String
    ): Response<ApiResponse<DailyHuggResponse>>
}