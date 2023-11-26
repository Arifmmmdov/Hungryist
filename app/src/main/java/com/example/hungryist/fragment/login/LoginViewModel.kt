package com.example.hungryist.fragment.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _liveData = MutableLiveData<String>()
    val liveData: LiveData<String> = _liveData

}