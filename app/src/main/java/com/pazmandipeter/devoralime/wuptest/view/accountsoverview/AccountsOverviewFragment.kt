package com.pazmandipeter.devoralime.wuptest.view.accountsoverview

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.pazmandipeter.devoralime.wuptest.R
import com.pazmandipeter.devoralime.wuptest.controller.IMainActivity
import com.pazmandipeter.devoralime.wuptest.databinding.AccountsOverviewFragmentBinding
import com.pazmandipeter.devoralime.wuptest.model.Account
import com.pazmandipeter.devoralime.wuptest.utils.Utilities.calculateProgress
import com.pazmandipeter.devoralime.wuptest.utils.thousandFormatting
import com.pazmandipeter.devoralime.wuptest.utils.toDateString
import com.pazmandipeter.devoralime.wuptest.view.accountsoverview.adapter.CardAdapter
import com.pazmandipeter.devoralime.wuptest.viewmodel.MainActivityViewModel
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

        setTitle()

        setupEvents()

        setupTabLayout()

        viewModel.getAccounts()

        setupArrows()

        binding.btnDetails.setOnClickListener {
            findNavController().navigate(R.id.action_accountsOverviewFragment_to_accountDetailsFragment)
        }

        viewModel.selectedItem.observe(viewLifecycleOwner, {
            updateUi(it.first)
            binding.viewPager.currentItem = it.second
        })
    }

    private fun setupArrows() {
        binding.ivArrowLeft.setOnClickListener {
            viewModel.onScrollLeft()
        }
        binding.ivArrowRight.setOnClickListener {
            viewModel.onScrollRight()
        }
    }

    private fun setTitle() {
        (activity as? IMainActivity)?.apply {
            setTitle(R.string.premium_card, 200L, null, Gravity.CENTER)
        }
    }

    private fun setupEvents() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.eventsChannel.collect { event ->
                when (event) {
                    MainActivityViewModel.Events.ShowLoading -> {
                        binding.progressBar.isVisible = true
                    }
                    is MainActivityViewModel.Events.ShowResult -> {
                        binding.progressBar.isGone = true
                        adapter.submitList(event.account)
                    }
                    is MainActivityViewModel.Events.ShowErrorMessage -> {
                        binding.progressBar.isGone = true

                        Snackbar.make(
                            binding.root, event.message, Snackbar.LENGTH_LONG
                        ).show()
                    }
                    is MainActivityViewModel.Events.ViewPagerScroll -> {
                        binding.viewPager.currentItem = event.currentIndex
                    }
                }
            }
        }
    }


    private fun updateUi(account: Account) {
        binding.adlAvailableBalance.setBalance(account.availableBalance.thousandFormatting())
        val progressValue = calculateProgress(
            account.availableBalance, account.currentBalance
        )
        ObjectAnimator.ofInt(binding.progressBarBalance, "progress", progressValue)
            .setDuration(500)
            .start()
        context?.let { _ ->
            if (progressValue == 0) {
                binding.adlAvailableBalance.setBalanceTextColor(R.color.red)
                binding.ivZeroBalanceAlert.isVisible = true
            } else {
                binding.adlAvailableBalance.setBalanceTextColor(R.color.blue)
                binding.ivZeroBalanceAlert.isGone = true
            }
        }

        setCurrentBalance(account)

        setMinPayment(account)

        setDueDate(account)
    }

    private fun setCurrentBalance(account: Account) {
        binding.adlCurrentBalance.setCurrency(account.currency)
        binding.adlCurrentBalance.setBalance(account.currentBalance.thousandFormatting())
    }

    private fun setMinPayment(account: Account) {
        binding.adlMinPayment.setCurrency(account.currency)
        binding.adlMinPayment.setBalance(account.minPayment.thousandFormatting())
    }

    private fun setDueDate(account: Account) {
        binding.adlDueDate.setBalance(account.dueDate.toDateString())
    }

    private fun setupTabLayout() {
        adapter = CardAdapter()
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position -> }.attach()

        if (viewModel.onPageChangeCallback == null) {
            viewModel.initPageChangeCallback()
        }
        binding.viewPager.registerOnPageChangeCallback(viewModel.onPageChangeCallback!!)
    }


}