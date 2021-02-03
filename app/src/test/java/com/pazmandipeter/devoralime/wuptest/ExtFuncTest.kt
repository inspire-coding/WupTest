package com.pazmandipeter.devoralime.wuptest

import com.google.common.truth.Truth.assertThat
import com.pazmandipeter.devoralime.wuptest.utils.thousandFormatting
import com.pazmandipeter.devoralime.wuptest.utils.toDateString
import org.junit.Test

class ExtFuncTest {

    @Test
    fun `invalid date format`() {

        // GIVEN
        val dateString = "26.09.2018"

        // WHEN
        val formattedDate = dateString.toDateString()

        // THEN
        assertThat(formattedDate).isEqualTo(dateString)

    }

    @Test
    fun `validating thousandFormatting() method`() {

        // GIVEN
        val decimalNumber_1 = 123456789
        val decimalNumber_2 = 89
        val decimalNumber_3 = 1234

        // WHEN
        val formattedDate_1 = decimalNumber_1.thousandFormatting()
        val formattedDate_2 = decimalNumber_2.thousandFormatting()
        val formattedDate_3 = decimalNumber_3.thousandFormatting()

        // THEN
        assertThat(formattedDate_1).isEqualTo("123’456’789.00")
        assertThat(formattedDate_2).isEqualTo("89.00")
        assertThat(formattedDate_3).isEqualTo("1’234.00")

    }
}