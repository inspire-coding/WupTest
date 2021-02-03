package com.pazmandipeter.devoralime.wuptest

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pazmandipeter.devoralime.wuptest.model.Account
import com.pazmandipeter.devoralime.wuptest.repository.AccountsRepository
import com.pazmandipeter.devoralime.wuptest.utils.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainActivityViewModel @ViewModelInject constructor(
    val accountsRepository: AccountsRepository
): ViewModel()  {

    // CONST
    private val TAG = this.javaClass.simpleName


    private val _eventsChannel = Channel<Events>()
    val eventsChannel = _eventsChannel.receiveAsFlow()

    private val _selectedItem = MutableLiveData<Pair<Account, Int>>()
    val selectedItem: LiveData<Pair<Account, Int>> = _selectedItem

    private val listOfAccounts = mutableListOf<Account>()

    private var selectedCardIndex = 0

    fun getAccounts() {
        viewModelScope.launch {
            accountsRepository.getAccounts().collect { result ->
                when(result)
                {
                    is Resource.Success -> {
                        result.data?.let { _listOfAccounts ->
                            listOfAccounts.addAll(_listOfAccounts)
                            _eventsChannel.send(Events.ShowResult(_listOfAccounts))
                            _selectedItem.postValue(Pair(_listOfAccounts[selectedCardIndex], selectedCardIndex))
                        }
                    }
                    is Resource.Loading -> {
                        _eventsChannel.send(Events.ShowLoading)
                    }
                    is Resource.Error -> {
                        result.message?.let { _message ->
                            _eventsChannel.send(Events.ShowErrorMessage(_message))
                        }
                    }
                }
            }
        }
    }



    /** Events **/
    fun onScrollLeft() {
        if (selectedCardIndex > 0) {
            selectedCardIndex--
            viewModelScope.launch {
                _eventsChannel.send(Events.ScrollLeft(selectedCardIndex, listOfAccounts[selectedCardIndex]))
                _selectedItem.postValue(Pair(listOfAccounts[selectedCardIndex], selectedCardIndex))
            }
        }
    }
    fun onScrollRight() {
        if (selectedCardIndex < listOfAccounts.size - 1) {
            selectedCardIndex++
            viewModelScope.launch {
                _eventsChannel.send(Events.ScrollRight(selectedCardIndex, listOfAccounts[selectedCardIndex]))
                _selectedItem.postValue(Pair(listOfAccounts[selectedCardIndex], selectedCardIndex))
            }
        }
    }


    sealed class Events {
        data class ScrollLeft(val currentIndex: Int, val selectedAccount: Account): Events()
        data class ScrollRight(val currentIndex: Int, val selectedAccount: Account): Events()
        object ShowLoading: Events()
        data class ShowResult(val account: List<Account>) : Events()
        data class ShowErrorMessage(val message: String) : Events()
    }


}