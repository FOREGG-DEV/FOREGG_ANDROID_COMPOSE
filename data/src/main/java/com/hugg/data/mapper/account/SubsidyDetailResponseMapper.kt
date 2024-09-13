package com.hugg.data.mapper.account

import com.hugg.data.base.Mapper
import com.hugg.data.dto.account.SubsidyDetailResponse
import com.hugg.domain.model.response.account.SubsidyDetailResponseVo

object SubsidyDetailResponseMapper: Mapper.ResponseMapper<SubsidyDetailResponse, SubsidyDetailResponseVo> {

    override fun mapDtoToModel(type: SubsidyDetailResponse?): SubsidyDetailResponseVo {
        return type?.run {
            SubsidyDetailResponseVo(
                nickname = nickname,
                content = content,
                amount = amount
            )
        }?: SubsidyDetailResponseVo()
    }
}