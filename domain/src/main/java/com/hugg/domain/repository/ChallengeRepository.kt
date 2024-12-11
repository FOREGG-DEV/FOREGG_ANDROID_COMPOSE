package com.hugg.domain.repository

import com.hugg.domain.base.ApiState
import com.hugg.domain.model.enums.CheerType
import com.hugg.domain.model.request.challenge.ChallengeNicknameVo
import com.hugg.domain.model.request.challenge.ChallengeThoughtsVo
import com.hugg.domain.model.request.challenge.CreateChallengeRequestVo
import com.hugg.domain.model.response.challenge.ChallengeCardVo
import com.hugg.domain.model.response.challenge.ChallengeSupportItemVo
import com.hugg.domain.model.response.challenge.MyChallengeListItemVo
import com.hugg.domain.model.response.challenge.MyChallengeVo
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    suspend fun getAllCommonChallenge(): Flow<ApiState<List<ChallengeCardVo>>>
    suspend fun getMyChallenge(): Flow<ApiState<MyChallengeVo>>
    suspend fun getNickname(): Flow<String?>
    suspend fun saveNickname(nickname: String)
    suspend fun removeNickname()
    suspend fun joinChallenge(request: ChallengeNicknameVo): Flow<ApiState<Unit>>
    suspend fun unlockChallenge(id: Long): Flow<ApiState<Unit>>
    suspend fun participateChallenge(id: Long): Flow<ApiState<Unit>>
    suspend fun getAllChallenge(): Flow<ApiState<List<ChallengeCardVo>>>
    suspend fun createChallenge(request: CreateChallengeRequestVo): Flow<ApiState<Unit>>
    suspend fun getChallengeSupportList(challengeId: Long, isSuccess: Boolean): Flow<ApiState<List<ChallengeSupportItemVo>>>
    suspend fun sendChallengeAction(challengeId: Long, cheerType: String, receiverId: Long): Flow<ApiState<Unit>>
    suspend fun getChallengeName(): Flow<ApiState<String>>
    suspend fun deleteChallenge(id: Long): Flow<ApiState<Unit>>
    suspend fun completeChallenge(id: Long, day: String, thoughts: ChallengeThoughtsVo): Flow<ApiState<Unit>>
    suspend fun supportChallenge(challengeId: Long, userId: Long, cheerType: CheerType): Flow<ApiState<Unit>>
}