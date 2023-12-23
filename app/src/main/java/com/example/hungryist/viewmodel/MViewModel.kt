package com.example.hungryist.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class MViewModel @Inject constructor(activity: Activity) : ViewModel() {

    @Inject
    lateinit var name: String;
    public fun getString() = name
}