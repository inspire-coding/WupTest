package com.pazmandipeter.devoralime.wuptest

import com.google.common.truth.Truth.assertThat
import com.pazmandipeter.devoralime.wuptest.utils.Utilities
import com.pazmandipeter.devoralime.wuptest.utils.toDateString
import org.junit.Test

class ProgressBarProgressCalc {

    @Test
    fun testProgressBarProgressCalc() {

        // GIVEN
        val firstNumber: Int = 1230
        val secondNumber: Int = 456
        val thirdNumber: Int = 789
        val forthNumber: Int = 1011
        val fifthNumber: Int = 1213

        // WHEN
        val progress = Utilities.calculateProgress(
            firstNumber, secondNumber, thirdNumber, forthNumber, fifthNumber
        )

        // THEN
        assertThat(progress).isEqualTo(26)
    }




}