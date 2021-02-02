package com.pazmandipeter.devoralime.wuptest.utils

import java.text.DecimalFormat

fun Int.thousandFormatting(): String {

    val df = DecimalFormat("###,###,###")
    return df.format(this)

}