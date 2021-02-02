package com.pazmandipeter.devoralime.wuptest.utils

import java.text.SimpleDateFormat
import java.util.*

fun String.toDateString(): String {

    val year = this.substring(0, 4).toIntOrNull()
    val month = this.substring(5, 7).toIntOrNull()
    val day = this.substring(8, 10).toIntOrNull()

    if (year != null && month != null && day != null) {
        val cal = Calendar.getInstance()
        cal.clear()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month-1)
        cal.set(Calendar.DAY_OF_MONTH, day)
        val format = SimpleDateFormat("dd.MM.yyy")
        return format.format(cal.time)
    }
    return this
}