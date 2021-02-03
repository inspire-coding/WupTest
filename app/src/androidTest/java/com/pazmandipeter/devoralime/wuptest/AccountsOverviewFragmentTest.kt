package com.pazmandipeter.devoralime.wuptest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.pazmandipeter.devoralime.wuptest.view.accountsoverview.AccountsOverviewFragment
import com.pazmandipeter.devoralime.wuptest.viewmodel.MainActivityViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AccountsOverviewFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun onViewCreated() {

        var testViewModel: MainActivityViewModel? = null

        launchFragmentInHiltContainer<AccountsOverviewFragment> {
            testViewModel = viewModel
        }

        testViewModel?.getAccounts()
//
        assertThat(testViewModel?.selectedItem?.getOrAwaitValue()).isNotNull()
    }
}