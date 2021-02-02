package com.pazmandipeter.devoralime.wuptest.di

import com.pazmandipeter.devoralime.wuptest.repository.AccountsRepository
import com.pazmandipeter.devoralime.wuptest.repository.AccountsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun providesRepository(
        accountsRepositoryImpl: AccountsRepositoryImpl
    ) : AccountsRepository


}