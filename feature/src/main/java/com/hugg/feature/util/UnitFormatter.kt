package com.hugg.feature.util

import java.text.NumberFormat
import java.util.Locale

object UnitFormatter {

    fun getMoneyFormat(money : Int) : String {
        val koreanFormat = NumberFormat.getNumberInstance(Locale("ko"))
        return koreanFormat.format(money) + "원"
    }
}