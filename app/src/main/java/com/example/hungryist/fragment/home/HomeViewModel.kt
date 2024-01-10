package com.example.hungryist.fragment.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.hungryist.model.BaseInfoModel
import com.example.hungryist.model.SelectStringModel
import com.example.hungryist.repo.Repository
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

    fun setSavedInfo(id: String, saved: Boolean) {
        repository.setDataSaved(id, saved)
    }

    fun onTypeSelected(selectStringModel: SelectStringModel) {

    }

    fun getDealsOfMonth(callback: (List<String>) -> Unit) {
        repository.getDealsOfMonths()
            .addOnSuccessListener {
                callback(it)
            }
            .addOnFailureListener {
                Log.d("MyTagHere", "getDealsOfMonth: ${it.message}")
                callback(listOf())
            }
    }
}