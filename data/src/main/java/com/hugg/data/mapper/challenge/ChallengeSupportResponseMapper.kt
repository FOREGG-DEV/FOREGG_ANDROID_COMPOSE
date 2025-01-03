package com.hugg.data.mapper.challenge

import com.hugg.data.base.Mapper
import com.hugg.data.dto.challenge.ChallengeSupportResponse
import com.hugg.domain.model.response.challenge.ChallengeSupportItemVo
import com.hugg.domain.model.response.challenge.ChallengeSupportResponseVo

object ChallengeSupportResponseMapper: Mapper.ResponseMapper<ChallengeSupportResponse, ChallengeSupportResponseVo> {
    override fun mapDtoToModel(type: ChallengeSupportResponse?): ChallengeSupportResponseVo {
        return type?.run {
            ChallengeSupportResponseVo(
                item = dto.map {
                    ChallengeSupportItemVo(
                        userId = it.id,
                        nickname = it.nickname,
                        thoughts = it.thoughts ?: "",
                        supported = it.supported
                    )
                },
                currentPage = currentPage,
                totalPage = totalPage,
            )
        } ?: ChallengeSupportResponseVo()
    }
}