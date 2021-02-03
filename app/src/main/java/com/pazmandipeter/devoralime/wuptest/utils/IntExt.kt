package com.pazmandipeter.devoralime.wuptest.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

fun Int.thousandFormatting(): String {

    val unusualSymbols = DecimalFormatSymbols(Locale("en_US"))
    unusualSymbols.decimalSeparator = '.'
    unusualSymbols.groupingSeparator = '’'

    val strange = "#,##0.00"
    val weirdFormatter = DecimalFormat(strange, unusualSymbols)
    weirdFormatter.groupingSize = 3

    return weirdFormatter.format(this)

}