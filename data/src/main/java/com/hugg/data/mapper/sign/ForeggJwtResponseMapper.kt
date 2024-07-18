package com.hugg.data.mapper.sign

import com.hugg.data.base.Mapper
import com.hugg.data.dto.ForeggJwtResponse
import com.hugg.domain.model.response.sign.ForeggJwtResponseVo

object ForeggJwtResponseMapper: Mapper.ResponseMapper<ForeggJwtResponse, ForeggJwtResponseVo> {

    override fun mapDtoToModel(type: ForeggJwtResponse?): ForeggJwtResponseVo {
        return type?.run {
            ForeggJwtResponseVo(accessToken, refreshToken)
        }?: ForeggJwtResponseVo("","")
    }
}