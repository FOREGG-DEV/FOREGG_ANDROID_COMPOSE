package com.hugg.data.api

import com.hugg.data.base.ApiResponse
import com.hugg.data.dto.challenge.AllChallengeResponse
import com.hugg.data.dto.challenge.ChallengeListResponse
import com.hugg.data.dto.challenge.MyChallengeResponseListItem
import com.hugg.domain.model.request.challenge.ChallengeNicknameVo
import com.hugg.domain.model.request.challenge.CreateChallengeRequestVo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ChallengeApi {

    companion object {
        const val PATH_ID = "id"
    }

    @GET(Endpoints.Challenge.CHALLENGE)
    suspend fun getAllCommonChallenge(): Response<ApiResponse<ChallengeListResponse>>

    @PATCH(Endpoints.Challenge.NICKNAME)
    suspend fun joinChallenge(
        @Body request :ChallengeNicknameVo
    ): Response<ApiResponse<Unit>>

    @PATCH(Endpoints.Challenge.UNLOCK)
    suspend fun unlockChallenge(
        @Path("id") id: Long
    ): Response<ApiResponse<Unit>>

    @PATCH(Endpoints.Challenge.PARTICIPATION)
    suspend fun participateChallenge(
        @Path("id") id: Long
    ): Response<ApiResponse<Unit>>

    @GET(Endpoints.Challenge.MY)
    suspend fun getMyChallenge(): Response<ApiResponse<List<MyChallengeResponseListItem>>>

    @GET(Endpoints.Challenge.ALL)
    suspend fun getAllChallenge(): Response<ApiResponse<AllChallengeResponse>>

    @POST(Endpoints.Challenge.CREATE)
    suspend fun createChallenge(
        @Body request: CreateChallengeRequestVo
    ): Response<ApiResponse<Unit>>
}