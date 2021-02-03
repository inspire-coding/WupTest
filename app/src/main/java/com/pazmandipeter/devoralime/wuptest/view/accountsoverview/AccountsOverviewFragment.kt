package com.pazmandipeter.devoralime.wuptest.view.accountsoverview

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pazmandipeter.devoralime.wuptest.MainActivity
import com.pazmandipeter.devoralime.wuptest.MainActivityViewModel
import com.pazmandipeter.devoralime.wuptest.R
import com.pazmandipeter.devoralime.wuptest.databinding.AccountsOverviewFragmentBinding
import com.pazmandipeter.devoralime.wuptest.model.Account
import com.pazmandipeter.devoralime.wuptest.utils.Utilities.calculateProgress
import com.pazmandipeter.devoralime.wuptest.utils.thousandFormatting
import com.pazmandipeter.devoralime.wuptest.utils.toDateString
import com.pazmandipeter.devoralime.wuptest.view.accountdetails.AccountDetailsFragment
import com.pazmandipeter.devoralime.wuptest.view.accountsoverview.adapter.CardAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AccountsOverviewFragment : Fragment(R.layout.accounts_overview_fragment) {

    private val viewModel: MainActivityViewModel by activityViewModels()
    private lateinit var binding: AccountsOverviewFragmentBinding
    private lateinit var adapter: CardAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = AccountsOverviewFragmentBinding.bind(view)

        viewModel.getAccounts()

        setupEvents()

        binding.ivArrowLeft.setOnClickListener {
            viewModel.onScrollLeft()
        }

        binding.ivArrowRight.setOnClickListener {
            viewModel.onScrollRight()
        }

        binding.btnDetails.setOnClickListener {
            findNavController().navigate(R.id.action_accountsOverviewFragment_to_accountDetailsFragment)
        }

        viewModel.selectedItem.observe(viewLifecycleOwner, {
            updateUi(it.first)
            binding.viewPager.currentItem = it.second
        })
    }

    private fun setupEvents() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.eventsChannel.collect { event ->
                when(event)
                {
                    MainActivityViewModel.Events.ShowLoading -> {
                        binding.progressBar.isVisible = true
                    }
                    is MainActivityViewModel.Events.ShowResult -> {
                        binding.progressBar.isGone = true
                        setupTabLayout(event.account)
                    }
                    is MainActivityViewModel.Events.ShowErrorMessage -> {
                        binding.progressBar.isGone = true

                        Snackbar.make(
                            binding.root, event.message, Snackbar.LENGTH_LONG
                        ).show()
                    }
                    is MainActivityViewModel.Events.ScrollLeft -> {
                        binding.viewPager.currentItem = event.currentIndex
                    }
                    is MainActivityViewModel.Events.ScrollRight -> {
                        binding.viewPager.currentItem = event.currentIndex
                    }
                }
            }
        }
    }


    private fun updateUi(account: Account) {
        binding.adlAvailableBalance.setBalance(account.availableBalance.thousandFormatting())
        val progressValue = calculateProgress(
            account.availableBalance, account.currentBalance, account.reservations
        )
        ObjectAnimator.ofInt(binding.progressBarBalance, "progress", progressValue)
            .setDuration(500)
            .start()
        context?.let { _context ->
            if(progressValue == 0) {
                binding.adlAvailableBalance.setBalanceTextColor(R.color.red)
                binding.ivZeroBalanceAlert.isVisible = true
            } else {
                binding.adlAvailableBalance.setBalanceTextColor(R.color.blue)
                binding.ivZeroBalanceAlert.isGone = true
            }
        }

        binding.adlCurrentBalance.setCurrency(account.currency)
        binding.adlCurrentBalance.setBalance(account.currentBalance.thousandFormatting())

        binding.adlMinPayment.setCurrency(account.currency)
        binding.adlMinPayment.setBalance(account.minPayment.thousandFormatting())

        binding.adlDueDate.setBalance(account.dueDate.toDateString())
    }



    private fun setupTabLayout(accounts: List<Account>) {
        adapter = CardAdapter()
        adapter.submitList(accounts)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position -> }.attach()
    }

}