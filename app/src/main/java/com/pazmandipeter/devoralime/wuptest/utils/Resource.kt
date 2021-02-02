package com.pazmandipeter.devoralime.wuptest.utils

sealed class Resource <out T> (val status : Status, val data : T?, val message: String?, val isLoading : Boolean = false) {

    data class Success<out R>(val _data : R?) : Resource<R>(
        status = Status.SUCCESS,
        data = _data,
        message = null
    )
    data class Loading(val _isLoading : Boolean) : Resource<Nothing>(
        status = Status.LOADING,
        data = null,
        message = null
    )
    data class Error(val exception: String) : Resource<Nothing>(
        status = Status.ERROR,
        data = null,
        message = exception
    )

}