package com.pazmandipeter.devoralime.wuptest.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

fun Int.thousandFormatting(): String {

    val df = DecimalFormat("#,##0.00")
    df.decimalFormatSymbols = DecimalFormatSymbols(Locale("en_US"))
    return df.format(this).replace(",", "’")

}