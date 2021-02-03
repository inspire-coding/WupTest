package com.pazmandipeter.devoralime.wuptest.repository

import com.pazmandipeter.devoralime.wuptest.model.Account
import com.pazmandipeter.devoralime.wuptest.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FakeAccountsRepositoryImpl @Inject constructor(): AccountsRepository {

    override suspend fun getAccounts() = flow<Resource<List<Account>>> {

        val fakeAccount_1 = Account().apply {
            cardHolderName = "Elon Musk"
            cardNumber = "9999-8888-7777-6666"
        }
        val fakeAccount_2 = Account().apply {
            cardHolderName = "Bill Gates"
            cardNumber = "5555-4444-3333-2222"
        }
        val fakeAccount_3 = Account().apply {
            cardHolderName = "Micimackó"
            cardNumber = "1111-1234-4321-0000"
        }
        val listOfAccounts = listOf<Account>(
            fakeAccount_1, fakeAccount_2, fakeAccount_3
        )

        emit(Resource.Success(listOfAccounts))

    }.catch { exception ->

        exception.message?.let { message ->
            emit(Resource.Error(message))
        }

    }.flowOn(Dispatchers.IO)
}