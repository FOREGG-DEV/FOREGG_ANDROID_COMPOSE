package com.hugg.data.mapper.notification

import com.hugg.data.base.Mapper
import com.hugg.data.dto.HomeResponse
import com.hugg.data.dto.notification.NotificationHistoryResponse
import com.hugg.domain.model.enums.DailyConditionType
import com.hugg.domain.model.response.home.HomeResponseVo
import com.hugg.domain.model.response.notification.NotificationHistoryItemResponseVo
import com.hugg.domain.model.response.notification.NotificationHistoryResponseVo

object NotificationHistoryResponseMapper: Mapper.ResponseMapper<NotificationHistoryResponse, NotificationHistoryResponseVo> {
    override fun mapDtoToModel(type: NotificationHistoryResponse?): NotificationHistoryResponseVo {
        return type?.run {
            NotificationHistoryResponseVo(
                itemList = type.dto.map {
                    NotificationHistoryItemResponseVo(
                        id = it.id,
                        targetKey = it.targetKey,
                        notificationType = it.notificationType,
                        sender = it.sender,
                        elapsedTime = it.elapsedTime
                    )
                },
                currentPage = currentPage,
                totalPage = totalPage
            )
        } ?: NotificationHistoryResponseVo()
    }
}