package com.hugg.data.mapper.dailyHugg

import com.hugg.data.base.Mapper

object DailyHuggSpecialQuestionResponseMapper: Mapper.ResponseMapper<String, String> {
    override fun mapDtoToModel(type: String?): String {
        return type ?: ""
    }
}