package com.hugg.data.repository

import com.hugg.data.api.ChallengeApi
import com.hugg.data.base.BaseRepository
import com.hugg.data.mapper.StringResponseMapper
import com.hugg.data.mapper.UnitResponseMapper
import com.hugg.data.mapper.challenge.AllChallengeResponseMapper
import com.hugg.data.mapper.challenge.ChallengeDetailResponseMapper
import com.hugg.data.mapper.challenge.ChallengeResponseMapper
import com.hugg.data.mapper.challenge.ChallengeSupportResponseMapper
import com.hugg.data.mapper.challenge.MyChallengeResponseMapper
import com.hugg.domain.base.ApiState
import com.hugg.domain.model.enums.CheerType
import com.hugg.domain.model.request.challenge.ChallengeNicknameVo
import com.hugg.domain.model.request.challenge.ChallengeThoughtsVo
import com.hugg.domain.model.request.challenge.CreateChallengeRequestVo
import com.hugg.domain.model.response.challenge.ChallengeCardVo
import com.hugg.domain.model.response.challenge.ChallengeSupportListVo
import com.hugg.domain.model.response.challenge.MyChallengeVo
import com.hugg.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val challengeApi: ChallengeApi,
) : ChallengeRepository, BaseRepository() {

    override suspend fun getAllCommonChallenge(): Flow<ApiState<List<ChallengeCardVo>>> {
        return apiLaunch(apiCall = { challengeApi.getAllCommonChallenge() }, ChallengeResponseMapper)
    }

    override suspend fun getMyChallenge(): Flow<ApiState<MyChallengeVo>> {
        return apiLaunch(apiCall = { challengeApi.getMyChallenge() }, MyChallengeResponseMapper)
    }

    override suspend fun joinChallenge(request: ChallengeNicknameVo): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { challengeApi.joinChallenge(request = request) }, UnitResponseMapper)
    }

    override suspend fun unlockChallenge(id: Long): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { challengeApi.unlockChallenge(id) }, UnitResponseMapper)
    }

    override suspend fun participateChallenge(id: Long): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { challengeApi.participateChallenge(id) }, UnitResponseMapper)
    }

    override suspend fun getAllChallenge(): Flow<ApiState<List<ChallengeCardVo>>> {
        return apiLaunch(apiCall = { challengeApi.getAllChallenge() }, AllChallengeResponseMapper)
    }

    override suspend fun createChallenge(request: CreateChallengeRequestVo): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { challengeApi.createChallenge(request = request) }, UnitResponseMapper)
    }

    override suspend fun getChallengeSupportList(challengeId: Long, isSuccess: Boolean, page: Int, size: Int): Flow<ApiState<ChallengeSupportListVo>> {
        return apiLaunch(apiCall = { challengeApi.getChallengeSupportList(challengeId, isSuccess, page, size) }, ChallengeSupportResponseMapper)
    }

    override suspend fun getChallengeName(): Flow<ApiState<String>> {
        return apiLaunch(apiCall = { challengeApi.getChallengeName() }, StringResponseMapper)
    }

    override suspend fun deleteChallenge(id: Long): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { challengeApi.deleteChallenge(id) }, UnitResponseMapper)
    }

    override suspend fun completeChallenge(id: Long, date: String, thoughts: ChallengeThoughtsVo): Flow<ApiState<String>> {
        return apiLaunch(apiCall = { challengeApi.completeChallenge(id, date, request = thoughts) }, StringResponseMapper)
    }

    override suspend fun supportChallenge(
        challengeId: Long,
        userId: Long,
        cheerType: CheerType
    ): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { challengeApi.supportChallenge(challengeId = challengeId, receiverId = userId, cheerType = cheerType) }, UnitResponseMapper)
    }

    override suspend fun searchChallenge(keyword: String): Flow<ApiState<List<ChallengeCardVo>>> {
        return apiLaunch(apiCall = { challengeApi.searchChallenge(keyword) }, AllChallengeResponseMapper)
    }

    override suspend fun getChallengeDetail(challengeId: Long): Flow<ApiState<ChallengeCardVo>> {
        return apiLaunch(apiCall = { challengeApi.getChallengeDetail(challengeId) }, ChallengeDetailResponseMapper)
    }
}