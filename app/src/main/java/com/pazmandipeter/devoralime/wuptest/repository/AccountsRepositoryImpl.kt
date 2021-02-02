package com.pazmandipeter.devoralime.wuptest.repository

import com.pazmandipeter.devoralime.wuptest.model.Account
import com.pazmandipeter.devoralime.wuptest.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AccountsRepositoryImpl @Inject constructor(
    private val accountsEndPoint: AccountsEndPoint
): AccountsRepository {

    override suspend fun getAccounts() = flow<Resource<List<Account>>>  {
        emit(Resource.Loading(true))

        val accounts = accountsEndPoint.getAccounts()

        emit(Resource.Success(accounts))

    }.catch { exception ->

        exception.message?.let { message ->
            emit(Resource.Error(message))
        }

    }.flowOn(Dispatchers.IO)
}