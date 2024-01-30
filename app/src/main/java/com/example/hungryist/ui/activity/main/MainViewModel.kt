package com.example.hungryist.ui.activity.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hungryist.repo.BaseRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val context: Context,
    private val repository: BaseRepository,
) : ViewModel() {
    private val _selectedTabID = MutableLiveData<Int>()
    val selectedTabId: LiveData<Int> = _selectedTabID


    fun setSelectedTab(selectedTabID: Int) {
        _selectedTabID.value = selectedTabID
    }

    fun getTabSelectedListener()= BottomNavigationView.OnNavigationItemSelectedListener { item ->
        setSelectedTab(item.itemId)
        true
    }

}