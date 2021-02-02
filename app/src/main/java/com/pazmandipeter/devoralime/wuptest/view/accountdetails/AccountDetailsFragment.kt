package com.pazmandipeter.devoralime.wuptest.view.accountdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.pazmandipeter.devoralime.wuptest.MainActivity
import com.pazmandipeter.devoralime.wuptest.MainActivityViewModel
import com.pazmandipeter.devoralime.wuptest.R
import com.pazmandipeter.devoralime.wuptest.databinding.AccountDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountDetailsFragment : Fragment(R.layout.account_details_fragment) {

    private val viewModel: MainActivityViewModel by activityViewModels()
    private lateinit var binding : AccountDetailsFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = AccountDetailsFragmentBinding.bind(view)
    }

}