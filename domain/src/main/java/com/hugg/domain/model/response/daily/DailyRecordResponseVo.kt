package com.hugg.domain.model.response.daily

import com.hugg.domain.model.vo.dailyRecord.DailyRecordResponseItemVo

data class DailyRecordResponseVo (
    val dailyResponseDto: List<DailyRecordResponseItemVo>
)