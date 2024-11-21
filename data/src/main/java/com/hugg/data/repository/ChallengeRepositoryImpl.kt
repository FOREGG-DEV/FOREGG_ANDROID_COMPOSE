package com.hugg.data.repository

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.hugg.data.api.ChallengeApi
import com.hugg.data.base.BaseRepository
import com.hugg.data.datastore.HuggDataStore
import com.hugg.data.mapper.UnitResponseMapper
import com.hugg.data.mapper.challenge.AllChallengeResponseMapper
import com.hugg.data.mapper.challenge.ChallengeResponseMapper
import com.hugg.data.mapper.challenge.MyChallengeResponseMapper
import com.hugg.domain.base.ApiState
import com.hugg.domain.model.request.challenge.ChallengeNicknameVo
import com.hugg.domain.model.response.challenge.AllChallengeItemVo
import com.hugg.domain.model.response.challenge.ChallengeCardVo
import com.hugg.domain.model.response.challenge.MyChallengeListItemVo
import com.hugg.domain.repository.ChallengeRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val challengeApi: ChallengeApi,
    private val dataStore: HuggDataStore
) : ChallengeRepository, BaseRepository() {

    companion object {
        const val LAST_VIEWED = "lastViewed_"
    }

    private val Context.dataStore by preferencesDataStore(name = "foregg_challenge_data_store")
    override suspend fun getAllCommonChallenge(): Flow<ApiState<List<ChallengeCardVo>>> {
        return apiLaunch(apiCall = { challengeApi.getAllCommonChallenge() }, ChallengeResponseMapper)
    }

    override suspend fun getMyChallenge(): Flow<ApiState<List<MyChallengeListItemVo>>> {
        return apiLaunch(apiCall = { challengeApi.getMyChallenge() }, MyChallengeResponseMapper)
    }

    override suspend fun getNickname(): Flow<String?> {
        return dataStore.challengeNickname
    }

    override suspend fun saveNickname(nickname: String) {
        return dataStore.saveNickname(nickname)
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

    override suspend fun getAllChallenge(): Flow<ApiState<List<AllChallengeItemVo>>> {
        return apiLaunch(apiCall = { challengeApi.getAllChallenge() }, AllChallengeResponseMapper)
    }
}