package com.pazmandipeter.devoralime.wuptest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AccountDetails(
    val accountLimit: Int = 0,
    val accountNumber: String = ""
) : Parcelable