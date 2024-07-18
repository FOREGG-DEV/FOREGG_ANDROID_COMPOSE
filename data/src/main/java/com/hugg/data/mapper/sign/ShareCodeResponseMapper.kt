package com.hugg.data.mapper.sign

import com.hugg.data.base.Mapper
import com.hugg.data.dto.SignResponse
import com.hugg.domain.model.response.sign.ShareCodeResponseVo

object ShareCodeResponseMapper: Mapper.ResponseMapper<SignResponse, ShareCodeResponseVo> {

    override fun mapDtoToModel(type: SignResponse?): ShareCodeResponseVo {
        return type?.run {
            ShareCodeResponseVo(
                shareCode = spouseCode ?: ""
            )
        }?: ShareCodeResponseVo()
    }
}