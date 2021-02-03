package com.pazmandipeter.devoralime.wuptest.utils

import com.pazmandipeter.devoralime.wuptest.R

object Utilities {

    const val BASE_URL = "https://raw.githubusercontent.com/"
    const val UNABLE_TO_RESOLVE_HOST = "Unable to resolve host \"raw.githubusercontent.com\": No address associated with hostname"

    val listOfCards = listOf(
        R.drawable.cccard,
        R.drawable.cccard2,
        R.drawable.cccard3
    )

    fun calculateProgress(vararg balances: Int): Int {
        return if(balances.size > 1) {
            val dividend = balances[0].toFloat()
            var divider = 0f
            for(balance in balances) {
                divider += balance
            }

            ((dividend / divider) * 100).toInt()
        } else {
            0
        }
    }
}