package com.hugg.data.mapper.account

import com.hugg.data.base.Mapper
import com.hugg.data.dto.account.SubsidyResponse
import com.hugg.domain.model.response.account.SubsidyListResponseVo

object SubsidyResponseMapper: Mapper.ResponseMapper<SubsidyResponse, List<SubsidyListResponseVo>> {

    override fun mapDtoToModel(type: SubsidyResponse?): List<SubsidyListResponseVo> {
        return type?.subsidyDetailResponseDTOS?.map {
            SubsidyListResponseVo(
                id = it.id,
                nickname = it.nickname,
                color = it.color,
                content = it.content,
                amount = it.amount,
                expenditure = it.expenditure,
                available = it.available,
                percent = it.percent
            )
        } ?: emptyList()
    }
}