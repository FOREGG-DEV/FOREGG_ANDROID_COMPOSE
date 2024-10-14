package com.hugg.data.mapper.dailyHugg

import com.hugg.data.base.Mapper
import com.hugg.data.dto.dailyHugg.DailyHuggListResponse
import com.hugg.domain.model.enums.DailyConditionType
import com.hugg.domain.model.response.dailyHugg.DailyHuggListItemVo
import com.hugg.domain.model.response.dailyHugg.DailyHuggListResponseVo

object DailyHuggListResponseMapper: Mapper.ResponseMapper<DailyHuggListResponse, DailyHuggListResponseVo> {
    override fun mapDtoToModel(type: DailyHuggListResponse?): DailyHuggListResponseVo {
        return type?.let {
            DailyHuggListResponseVo(
                dailyHuggList = it.dto.map { item ->
                    DailyHuggListItemVo(
                        id = item.id,
                        date = item.date,
                        dailyConditionType = DailyConditionType.fromValue(item.dailyConditionType) ?: DailyConditionType.DEFAULT,
                        content = item.content
                    )
                },
                currentPage = it.currentPage,
                totalItems = it.totalItems,
                totalPages = it.totalPages
            )
        } ?: DailyHuggListResponseVo()
    }
}