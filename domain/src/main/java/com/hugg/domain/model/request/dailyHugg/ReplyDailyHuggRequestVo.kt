package com.hugg.domain.model.request.dailyHugg

import com.google.gson.annotations.SerializedName
import com.hugg.domain.model.enums.DailyHuggReplyType

data class ReplyDailyHuggRequestVo(
    @SerializedName("id")
    val id : Long,
    @SerializedName("replyEmojiType")
    val replyEmojiType : DailyHuggReplyType,
    @SerializedName("content")
    val content : String,
)
