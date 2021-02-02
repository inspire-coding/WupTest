package com.pazmandipeter.devoralime.wuptest.repository

import com.pazmandipeter.devoralime.wuptest.model.Account
import retrofit2.http.GET

interface AccountsEndPoint {

    @GET("wupdigital/interview-api/master/api/v1/cards.json")
    suspend fun getAccounts(): List<Account>

}