package com.example.hungryist.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hungryist.model.BaseInfoModel
import com.example.hungryist.model.SelectStringModel
import com.example.hungryist.repo.Repository
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _isDealsOfMonthLoading = MutableLiveData<Boolean>(false)
    val isDealsOfMonthLoading: LiveData<Boolean> = _isDealsOfMonthLoading

    private val _isTopPlacesLoading = MutableLiveData<Boolean>(false)
    val isTopPlacesLoading: LiveData<Boolean> = _isTopPlacesLoading

    fun getBaseInfoModel(callback: (List<BaseInfoModel>) -> Unit) {
        _isTopPlacesLoading.value = true
        repository.getBaseInfoList()
            .addOnSuccessListener {
                callback(it.filter { !it.titleName.isNullOrEmpty() })
                _isTopPlacesLoading.value = false
            }
            .addOnFailureListener {
                callback(listOf())
                _isTopPlacesLoading.value = false
            }

    }

    fun setSavedInfo(id: String, saved: Boolean) {
        repository.setDataSaved(id, saved)
    }

    fun onTypeSelected(selectStringModel: SelectStringModel) {

    }

    fun getDealsOfMonth(callback: (List<String>) -> Unit) {
        _isDealsOfMonthLoading.value = true
        repository.getDealsOfMonths()
            .addOnSuccessListener {
                callback(it)
                _isDealsOfMonthLoading.value = false
            }
            .addOnFailureListener {
                callback(listOf())
                _isDealsOfMonthLoading.value = false
            }
    }
}