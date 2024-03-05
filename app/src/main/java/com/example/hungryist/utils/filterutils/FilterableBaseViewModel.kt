package com.example.hungryist.utils.filterutils

import android.content.Context
import androidx.lifecycle.ViewModel

abstract class FilterableBaseViewModel : ViewModel() {
    abstract fun onTypeSelected(context: Context, name: String)
    abstract fun removeCustomFilter()
}