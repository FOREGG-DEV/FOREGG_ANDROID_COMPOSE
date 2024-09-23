package com.hugg.domain.model.request.dailyHugg

import com.hugg.domain.model.vo.dailyHugg.CreateDailyHuggDto
import okhttp3.MultipartBody

data class CreateDailyHuggVo(
    val image: MultipartBody.Part,
    val dto: CreateDailyHuggDto
)
