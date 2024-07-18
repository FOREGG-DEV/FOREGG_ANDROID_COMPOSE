package com.hugg.domain.model.request.sign

data class SaveForeggJwtRequestVo(
    val accessToken : String,
    val refreshToken : String
)
