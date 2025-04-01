package com.example.marcusfitnesstracker.data

import UserSetupRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserSetupViewModel(private val repository: UserSetupRepository) : ViewModel() {

    private val _userSetup = MutableLiveData<UserSetup?>()
    val userSetup: LiveData<UserSetup?> get() = _userSetup

    fun saveUserSetup(setup: UserSetup) {
        viewModelScope.launch {
            repository.saveSetup(setup)
            _userSetup.postValue(setup)
        }
    }

    fun loadUserSetup() {
        viewModelScope.launch {
            val setup = repository.getSetup()
            _userSetup.postValue(setup)
        }
    }
}
