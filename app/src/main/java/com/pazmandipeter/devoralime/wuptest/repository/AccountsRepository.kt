package com.pazmandipeter.devoralime.wuptest.repository

import com.pazmandipeter.devoralime.wuptest.model.Account
import com.pazmandipeter.devoralime.wuptest.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AccountsRepository {

    suspend fun getAccounts(): Flow<Resource<List<Account>>>

}