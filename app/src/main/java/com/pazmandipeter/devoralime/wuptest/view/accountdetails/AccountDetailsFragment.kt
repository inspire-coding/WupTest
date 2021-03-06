package com.pazmandipeter.devoralime.wuptest.view.accountdetails

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.pazmandipeter.devoralime.wuptest.viewmodel.MainActivityViewModel
import com.pazmandipeter.devoralime.wuptest.R
import com.pazmandipeter.devoralime.wuptest.controller.MainActivity
import com.pazmandipeter.devoralime.wuptest.databinding.AccountDetailsFragmentBinding
import com.pazmandipeter.devoralime.wuptest.model.Account
import com.pazmandipeter.devoralime.wuptest.utils.Utilities.calculateProgress
import com.pazmandipeter.devoralime.wuptest.utils.hideCardNumbers
import com.pazmandipeter.devoralime.wuptest.utils.thousandFormatting
import com.pazmandipeter.devoralime.wuptest.utils.toDateString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AccountDetailsFragment : Fragment(R.layout.account_details_fragment) {

    private val viewModel: MainActivityViewModel by activityViewModels()
    private lateinit var binding : AccountDetailsFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = AccountDetailsFragmentBinding.bind(view)

        setupTitle()
        setupEvents()

        viewModel.selectedItem.observe(viewLifecycleOwner, {
            updateUi(it.first)
        })
    }



    private fun setupTitle() {
        (activity as? MainActivity)?.apply {
            setTitle(R.string.details, 200L, outDuration = 0, Gravity.START)
        }
    }


    private fun setupEvents() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.eventsChannel.collect { event ->
                when(event)
                {
                    is MainActivityViewModel.Events.ShowResult -> {
                        if(event.account.isNotEmpty()) {
                            println(event.account[0])
                            updateUi(event.account[0])
                        }
                    }
                    is MainActivityViewModel.Events.ViewPagerScroll -> {
                        updateUi(event.selectedAccount)
                    }
                }
            }
        }
    }

    private fun updateUi(account: Account) {
        binding.tvCurrentBalance.text = account.currentBalance.thousandFormatting()
        binding.tvAvailableBalance.text = account.availableBalance.thousandFormatting()
//
        val progressCurrentBalance = calculateProgress(
            account.currentBalance, account.availableBalance, account.reservations
        )
        binding.progressBarCurrentBalance.progress = progressCurrentBalance
        val progressAvailableBalance = calculateProgress(
            account.availableBalance, account.currentBalance, account.reservations
        )
        binding.progressBarAvailable.progress = progressAvailableBalance

        binding.adlBalanceCarriedOver.setCurrency(account.currency)
        binding.adlBalanceCarriedOver.setBalance(account.balanceCarriedOverFromLastStatement.thousandFormatting())

        binding.adlReservations.setCurrency(account.currency)
        binding.adlReservations.setBalance(account.reservations.thousandFormatting())

        binding.adlTotalSpendingsSince.setCurrency(account.currency)
        binding.adlTotalSpendingsSince.setBalance(account.spendingsSinceLastStatement.thousandFormatting())

        binding.adlYourLastPayment.setBalance(account.yourLastRepayment.toDateString())

        binding.adlCardAccountLimit.setCurrency(account.currency)
        binding.adlCardAccountLimit.setBalance(account.accountDetails.accountLimit.thousandFormatting())

        binding.adlCardAccountNumber.setBalance(account.accountDetails.accountNumber)

        binding.adlCardNumber.setBalance(account.cardNumber.hideCardNumbers())
        binding.adlCardHolderName.setBalance(account.cardHolderName)

        if(!account.supplementaryCardNumber.isNullOrEmpty() && !account.supplementaryCardHolderName.isNullOrEmpty()) {
            binding.adlSupplementaryCardNumber.setBalance(account.supplementaryCardNumber.hideCardNumbers())
            binding.adlSupplementaryCardHolderName.setBalance(account.supplementaryCardHolderName!!)

            binding.adlSupplementaryCardNumber.isVisible = true
            binding.adlSupplementaryCardNumber.isVisible = true
            binding.adlSupplementaryCardHolderName.isVisible = true

        } else {
            binding.tvSupplementaryCard.isGone = true
            binding.adlSupplementaryCardNumber.isGone = true
            binding.adlSupplementaryCardHolderName.isGone = true

        }
    }



}