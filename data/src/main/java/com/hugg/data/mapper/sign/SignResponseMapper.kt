package com.hugg.data.mapper.sign

import com.hugg.data.base.Mapper
import com.hugg.data.dto.SignResponse
import com.hugg.domain.model.response.sign.SignResponseVo

object SignResponseMapper: Mapper.ResponseMapper<SignResponse, SignResponseVo> {

    override fun mapDtoToModel(type: SignResponse?): SignResponseVo {
        return type?.run {
            SignResponseVo(
                accessToken = accessToken ?: "",
                refreshToken = refreshToken ?: "",
                shareCode = spouseCode ?: ""
            )
        }?: SignResponseVo()
    }
}