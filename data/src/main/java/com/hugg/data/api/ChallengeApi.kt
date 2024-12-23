package com.hugg.data.api

import com.hugg.data.base.ApiResponse
import com.hugg.data.dto.challenge.ChallengeListResponse
import com.hugg.data.dto.challenge.ChallengeResponseListItem
import com.hugg.data.dto.challenge.ChallengeSupportResponseItem
import com.hugg.data.dto.challenge.MyChallengeResponse
import com.hugg.domain.model.enums.CheerType
import com.hugg.domain.model.request.challenge.ChallengeNicknameVo
import com.hugg.domain.model.request.challenge.ChallengeThoughtsVo
import com.hugg.domain.model.request.challenge.CreateChallengeRequestVo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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
        @Path(PATH_ID) id: Long
    ): Response<ApiResponse<Unit>>

    @PATCH(Endpoints.Challenge.PARTICIPATION)
    suspend fun participateChallenge(
        @Path(PATH_ID) id: Long
    ): Response<ApiResponse<Unit>>

    @GET(Endpoints.Challenge.MY)
    suspend fun getMyChallenge(): Response<ApiResponse<MyChallengeResponse>>

    @GET(Endpoints.Challenge.ALL)
    suspend fun getAllChallenge(): Response<ApiResponse<ChallengeListResponse>>

    @POST(Endpoints.Challenge.CREATE)
    suspend fun createChallenge(
        @Body request: CreateChallengeRequestVo
    ): Response<ApiResponse<Unit>>

    @GET(Endpoints.Challenge.GET_SUPPORT)
    suspend fun getChallengeSupportList(
        @Path("challengeId") challengeId: Long,
        @Path("isSuccess") isSuccess: Boolean
    ): Response<ApiResponse<List<ChallengeSupportResponseItem>>>

    @POST(Endpoints.Challenge.ACTION)
    suspend fun sendChallengeAction(
        @Path("challengeId") id: Long,
        @Query("cheerType") cheerType: String,
        @Query("receiverId") receiverId: Long
    ): Response<ApiResponse<Unit>>

    @GET(Endpoints.Challenge.NAME)
    suspend fun getChallengeName(): Response<ApiResponse<String>>

    @DELETE(Endpoints.Challenge.DELETE)
    suspend fun deleteChallenge(
        @Path(PATH_ID) id: Long
    ): Response<ApiResponse<Unit>>

    @PATCH(Endpoints.Challenge.COMPLETE)
    suspend fun completeChallenge(
        @Path(PATH_ID) id: Long,
        @Query("day") day: String,
        @Body request: ChallengeThoughtsVo
    ): Response<ApiResponse<Unit>>

    @POST(Endpoints.Challenge.SUPPORT)
    suspend fun supportChallenge(
        @Path("challengeId") challengeId: Long,
        @Query("cheerType") cheerType: CheerType,
        @Query("receiverId") receiverId: Long
    ): Response<ApiResponse<Unit>>

    @GET(Endpoints.Challenge.SEARCH)
    suspend fun searchChallenge(
        @Query("keyword") keyword: String
    ): Response<ApiResponse<ChallengeListResponse>>

    @GET(Endpoints.Challenge.DETAIL)
    suspend fun getChallengeDetail(
        @Path("challengeId") challengeId: Long
    ): Response<ApiResponse<ChallengeResponseListItem>>
}