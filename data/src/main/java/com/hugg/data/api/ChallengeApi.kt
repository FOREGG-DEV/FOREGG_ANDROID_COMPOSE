package com.hugg.data.api

import com.hugg.data.base.ApiResponse
import com.hugg.data.dto.challenge.ChallengeListResponse
import com.hugg.data.dto.challenge.ChallengeResponseListItem
import com.hugg.data.dto.challenge.MyChallengeResponseListItem
import com.hugg.domain.model.request.challenge.ChallengeNicknameVo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ChallengeApi {

    companion object {
        const val PATH_ID = "id"
    }

    @GET(Endpoints.Challenge.CHALLENGE)
    suspend fun getAllChallenge(): Response<ApiResponse<ChallengeListResponse>>

    @PATCH(Endpoints.Challenge.NICKNAME)
    suspend fun joinChallenge(
        @Body request :ChallengeNicknameVo
    ): Response<ApiResponse<Unit>>




    @POST(Endpoints.Challenge.PARTICIPATION)
    suspend fun participateChallenge(
        @Path(PATH_ID) id: Long
    ): Response<ApiResponse<Unit>>

    @DELETE(Endpoints.Challenge.QUIT)
    suspend fun quitChallenge(
        @Path(PATH_ID) id: Long
    ): Response<ApiResponse<Unit>>

    @POST(Endpoints.Challenge.COMPLETE)
    suspend fun completeChallenge(
        @Path(PATH_ID) id: Long
    ): Response<ApiResponse<Unit>>

    @DELETE(Endpoints.Challenge.DELETE_COMPLETE)
    suspend fun deleteCompleteChallenge(
        @Path(PATH_ID) id: Long
    ): Response<ApiResponse<Unit>>

    @GET(Endpoints.Challenge.MY)
    suspend fun getMyChallenge(): Response<ApiResponse<List<MyChallengeResponseListItem>>>
}