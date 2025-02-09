package com.hugg.data.mapper.challenge

import android.util.Log
import com.hugg.data.base.Mapper
import com.hugg.data.dto.challenge.MyChallengeResponse
import com.hugg.data.dto.challenge.MyChallengeResponseListItem
import com.hugg.domain.model.response.challenge.MyChallengeListItemVo
import com.hugg.domain.model.response.challenge.MyChallengeVo

object MyChallengeResponseMapper: Mapper.ResponseMapper<MyChallengeResponse, MyChallengeVo> {
    override fun mapDtoToModel(type: MyChallengeResponse?): MyChallengeVo {
        return type?.let {
            MyChallengeVo(
                dtos = it.dtos
            )
        } ?: MyChallengeVo()
    }
}