package com.hugg.feature.util

import com.hugg.feature.theme.ACCOUNT_ROUND_UNIT
import com.hugg.feature.theme.ACCOUNT_SUBSIDY
import java.text.NumberFormat
import java.util.Locale

object UnitFormatter {

    fun getMoneyFormat(money : Int) : String {
        val koreanFormat = NumberFormat.getNumberInstance(Locale("ko"))
        return koreanFormat.format(money) + "원"
    }

    fun getRoundFormat(round : Int) : String {
        return "$round$ACCOUNT_ROUND_UNIT"
    }

    fun getSubsidyTitleFormat(title : String) : String {
        return "지원금($title)"
    }
}