package com.hugg.data.repository

import com.hugg.data.api.DailyHuggApi
import com.hugg.data.base.BaseRepository
import com.hugg.data.mapper.UnitResponseMapper
import com.hugg.domain.base.ApiState
import com.hugg.domain.model.request.dailyHugg.CreateDailyHuggVo
import com.hugg.domain.model.vo.dailyHugg.CreateDailyHuggDto
import com.hugg.domain.repository.DailyHuggRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class DailyHuggRepositoryImpl @Inject constructor(
    private val dailyHuggApi: DailyHuggApi
): DailyHuggRepository, BaseRepository() {
    override suspend fun createDailyHugg(
        image: MultipartBody.Part,
        dto: RequestBody
    ): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { dailyHuggApi.createDailyHugg(image = image, dto = dto) }, UnitResponseMapper)
    }
}