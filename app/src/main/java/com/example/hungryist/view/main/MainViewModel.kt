package com.example.hungryist.view.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hungryist.model.BaseInfoModel
import com.example.hungryist.repo.Repository
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val context: Context,
    private val repository: Repository,
) : ViewModel() {
    private val _selectedTabID = MutableLiveData<Int>()
    val selectedTabId: LiveData<Int> = _selectedTabID


    fun setSelectedTab(selectedTabID: Int) {
        _selectedTabID.value = selectedTabID
    }
}