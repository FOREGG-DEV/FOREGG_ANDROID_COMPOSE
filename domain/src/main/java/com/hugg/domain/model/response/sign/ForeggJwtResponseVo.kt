package com.hugg.domain.model.response.sign

data class ForeggJwtResponseVo (
    val accessToken : String = "",
    val refreshToken : String = "",
) {
    val isTokenValid = accessToken.isNotBlank() && refreshToken.isNotBlank()
}