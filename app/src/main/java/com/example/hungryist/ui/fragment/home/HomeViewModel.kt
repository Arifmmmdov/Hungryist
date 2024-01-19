package com.example.hungryist.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hungryist.model.BaseInfoModel
import com.example.hungryist.model.SelectPairString
import com.example.hungryist.model.SelectStringModel
import com.example.hungryist.repo.Repository
import com.example.hungryist.utils.FilterUtils
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _isDealsOfMonthLoading = MutableLiveData(false)
    val isDealsOfMonthLoading: LiveData<Boolean> = _isDealsOfMonthLoading

    private val _isTopPlacesLoading = MutableLiveData(false)
    val isTopPlacesLoading: LiveData<Boolean> = _isTopPlacesLoading

    private val _isPlacesLoading = MutableLiveData(false)
    val isPlacesLoading: LiveData<Boolean> = _isPlacesLoading

    private val _baseInfoList = MutableLiveData<List<BaseInfoModel>>()
    val baseInfoList: LiveData<List<BaseInfoModel>> = _baseInfoList

    private val _filteredBaseInfoList = MutableLiveData<List<BaseInfoModel>>()
    val filteredBaseInfoList: LiveData<List<BaseInfoModel>> = _filteredBaseInfoList

    private val selectedPairString = MutableLiveData<SelectPairString>()

    fun getBaseInfoList() {
        _isTopPlacesLoading.value = true
        repository.getBaseInfoList()
            .addOnSuccessListener {
                _baseInfoList.value = it
            }
            .addOnFailureListener {
                _baseInfoList.value = listOf()
            }.addOnCompleteListener {
                _isTopPlacesLoading.value = false
            }

    }

    fun getPlaces(callback: (List<SelectStringModel>) -> Unit) {
        _isPlacesLoading.value = true
        repository.getPlacesList()
            .addOnSuccessListener {
                callback(it)
            }
            .addOnFailureListener {
                callback(listOf())
            }
            .addOnCompleteListener {
                _isPlacesLoading.value = false
            }

    }


    fun setSavedInfo(id: String, referenceId: String, saved: Boolean) {
        repository.setDataSaved(id, referenceId, saved)
    }

    fun onTypeSelected(selectStringModel: SelectStringModel) {
        _filteredBaseInfoList.value = FilterUtils.filterForCategory(selectStringModel.name)
    }

    fun getDealsOfMonth(callback: (List<String>) -> Unit) {
        _isDealsOfMonthLoading.value = true
        repository.getDealsOfMonths()
            .addOnSuccessListener {
                callback(it)
            }
            .addOnFailureListener {
                callback(listOf())
            }
            .addOnCompleteListener {
                _isDealsOfMonthLoading.value = false
            }
    }

    fun onTextTyped(text: String) {
        _filteredBaseInfoList.value = FilterUtils.filterForTypedText(text)
    }

}