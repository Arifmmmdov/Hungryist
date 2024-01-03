package com.example.hungryist.view.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _selectedTabID = MutableLiveData<Int>()
    val selectedTabId = _selectedTabID

    fun setSelectedTab(selectedTabID: Int) {
        _selectedTabID.value = selectedTabID
    }
}