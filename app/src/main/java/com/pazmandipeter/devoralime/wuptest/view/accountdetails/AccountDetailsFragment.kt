package com.pazmandipeter.devoralime.wuptest.view.accountdetails

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.pazmandipeter.devoralime.wuptest.MainActivity
import com.pazmandipeter.devoralime.wuptest.MainActivityViewModel
import com.pazmandipeter.devoralime.wuptest.R
import com.pazmandipeter.devoralime.wuptest.databinding.AccountDetailsFragmentBinding
import com.pazmandipeter.devoralime.wuptest.model.Account
import com.pazmandipeter.devoralime.wuptest.utils.Utilities.calculateProgress
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

        setupEvents()

        viewModel.selectedItem.observe(viewLifecycleOwner, {
            updateUi(it.first)
        })
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
                    is MainActivityViewModel.Events.ScrollLeft -> {
                        updateUi(event.selectedAccount)
                    }
                    is MainActivityViewModel.Events.ScrollRight -> {
                        updateUi(event.selectedAccount)
                    }
                }
            }
        }
    }

    private fun updateUi(account: Account) {
        binding.tvCurrentBalance.text = account.currentBalance.thousandFormatting()
        binding.tvAvailableBalance.text = account.availableBalance.thousandFormatting()

        val progressCurrentBalance = calculateProgress(
            account.currentBalance, account.availableBalance, account.reservations
        )
        binding.progressBarCurrentBalance.progress = progressCurrentBalance
        val progressAvailableBalance = calculateProgress(
            account.availableBalance, account.currentBalance, account.reservations
        )
        binding.progressBarAvailable.progress = progressAvailableBalance
    }



}