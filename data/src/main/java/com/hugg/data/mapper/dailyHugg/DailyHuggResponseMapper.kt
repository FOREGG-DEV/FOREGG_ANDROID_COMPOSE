package com.hugg.data.mapper.dailyHugg

import com.hugg.data.base.Mapper
import com.hugg.data.dto.dailyHugg.DailyHuggResponse
import com.hugg.domain.model.enums.DailyConditionType
import com.hugg.domain.model.enums.DailyHuggReplyType
import com.hugg.domain.model.response.dailyHugg.DailyHuggItemVo

object DailyHuggResponseMapper: Mapper.ResponseMapper<DailyHuggResponse, DailyHuggItemVo> {
    override fun mapDtoToModel(type: DailyHuggResponse?): DailyHuggItemVo {
        return type?.let {
            DailyHuggItemVo(
                id = it.id,
                date = it.date,
                day = it.day,
                content = it.content,
                imageUrl = it.imageUrl,
                reply = it.replyContent ?: "",
                replyEmojiType = it.replyEmojiType ?: DailyHuggReplyType.NOTHING,
                specialQuestion = it.specialQuestion ?: "",
                dailyConditionType = it.dailyConditionType
            )
        } ?: DailyHuggItemVo()
    }
}