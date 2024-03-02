package com.example.hungryist.utils.filterutils

import androidx.lifecycle.ViewModel

abstract class FilterableBaseViewModel : ViewModel() {
    abstract fun onTypeSelected(name: String)
    abstract fun removeCustomFilter()
}