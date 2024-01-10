package com.example.hungryist.fragment.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.hungryist.model.BaseInfoModel
import com.example.hungryist.repo.Repository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    fun getBaseInfoModel(callback: (List<BaseInfoModel>) -> Unit) {
        repository.getBaseInfoList()
            .addOnSuccessListener {
                callback(it)
            }
            .addOnFailureListener {
                callback(listOf())
            }
    }
}