package com.hugg.data.mapper.challenge

import com.hugg.data.base.Mapper
import com.hugg.data.dto.challenge.ChallengeSupportResponse
import com.hugg.data.dto.challenge.ChallengeSupportResponseItem
import com.hugg.domain.model.response.challenge.ChallengeSupportItemVo
import com.hugg.domain.model.response.challenge.ChallengeSupportListVo

object ChallengeSupportResponseMapper: Mapper.ResponseMapper<ChallengeSupportResponse, ChallengeSupportListVo> {
    override fun mapDtoToModel(type: ChallengeSupportResponse?): ChallengeSupportListVo {
        return type?.let {
            ChallengeSupportListVo(
                items = it.dto.map { item ->
                    ChallengeSupportItemVo(
                        userId = item.id,
                        nickname = item.nickname,
                        thoughts = item.thoughts,
                        supported = item.supported
                    )
                },
                totalItems = it.totalItems,
                currentPage = it.currentPage,
                totalPage = it.totalPage
            )
        } ?: ChallengeSupportListVo()
    }
}