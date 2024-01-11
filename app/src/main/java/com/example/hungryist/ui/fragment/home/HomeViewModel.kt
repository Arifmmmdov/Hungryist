package com.example.hungryist.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hungryist.model.BaseInfoModel
import com.example.hungryist.model.SelectPairString
import com.example.hungryist.model.SelectStringModel
import com.example.hungryist.repo.Repository
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

    fun getBaseInfoModel() {
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
                _isPlacesLoading.value = false
            }
            .addOnFailureListener {
                callback(listOf())
                _isPlacesLoading.value = false
            }

    }


    fun setSavedInfo(id: String, saved: Boolean) {
        repository.setDataSaved(id, saved)
    }

    fun onTypeSelected(selectStringModel: SelectStringModel) {
        selectedPairString.value?.place = selectStringModel.place
        filterItems()
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

    fun onTextTyped(text: String) {
        selectedPairString.value?.typed = text

        filterItems()
    }

    private fun filterItems() {
        var result = baseInfoList.value
        if (!selectedPairString.value?.place.isNullOrEmpty())
            result = result?.filter {
                it.type == selectedPairString.value?.place
            }
        if (selectedPairString.value?.typed != "") {
            result = result?.filter {
                selectedPairString.value?.typed?.let { it1 -> it.name.contains(it1) } ?: true
            }
        }
        _filteredBaseInfoList.value = result
    }
}