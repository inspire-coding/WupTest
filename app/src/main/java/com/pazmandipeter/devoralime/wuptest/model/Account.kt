package com.pazmandipeter.devoralime.wuptest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Account(
    val accountDetails: AccountDetails = AccountDetails(),
    val availableBalance: Int = 0,
    val balanceCarriedOverFromLastStatement: Int? = 0,
    var cardHolderName: String? = "",
    val cardId: String? = "",
    val cardImage: String = "",
    val cardNumber: String? = "",
    val currency: String? = "",
    val currentBalance: Int = 0,
    val cvv: String? = "",
    val dueDate: String = "",
    val expirationDate: String? = "",
    val friendlyName: String? = "",
    val issuer: String? = "",
    val minPayment: Int = 0,
    val reservations: Int = 0,
    val spendingsSinceLastStatement: Int? = 0,
    val status: String? = "",
    val yourLastRepayment: String? = ""
) : Parcelable