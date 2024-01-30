package com.example.hungryist.ui.fragment.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hungryist.model.BaseInfoModel
import com.example.hungryist.model.OpenCloseStatusModel
import com.example.hungryist.model.SelectPairString
import com.example.hungryist.model.SelectStringModel
import com.example.hungryist.repo.BaseRepository
import com.example.hungryist.utils.SharedPreferencesManager
import com.example.hungryist.utils.extension.showToastMessage
import com.example.hungryist.utils.filterutils.HomePageFilterUtils
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val context: Context,
    private val repository: BaseRepository,
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

    fun getBaseList() {
        _isTopPlacesLoading.value = true
        repository.getBaseInfoList().addOnSuccessListener {
            getOpenCloseDateList(it)
        }.addOnFailureListener {
            _baseInfoList.value = listOf()
        }.addOnCompleteListener {
            _isTopPlacesLoading.value = false
        }

    }

    private fun getOpenCloseDateList(baseInfoModels: MutableList<BaseInfoModel>) {
        val tasks = mutableListOf<Task<List<OpenCloseStatusModel>>>()

        baseInfoModels.forEach { baseList ->
            val task = repository.getOpenCloseDate(baseList.id)
                .addOnSuccessListener {
                    baseList.openCloseTimes = it
                }
                .addOnFailureListener {
                    context.showToastMessage(it.message.toString())
                }

            tasks.add(task)
        }

        Tasks.whenAll(tasks)
            .addOnSuccessListener {
                _baseInfoList.value = baseInfoModels
            }
            .addOnFailureListener {
                context.showToastMessage(it.message.toString())
            }

    }

    fun getPlaces(callback: (List<SelectStringModel>) -> Unit) {
        _isPlacesLoading.value = true
        repository.getPlacesList().addOnSuccessListener {
            callback(it)
        }.addOnFailureListener {
            callback(listOf())
        }.addOnCompleteListener {
            _isPlacesLoading.value = false
        }

    }


    fun setSavedInfo(id: String, referenceId: String, saved: Boolean) {
        repository.setDataSaved(id, referenceId, saved)
    }

    fun onTypeSelected(selectStringModel: SelectStringModel?) {
        _filteredBaseInfoList.value = HomePageFilterUtils.filterForCategory(selectStringModel?.name)
    }

    fun getDealsOfMonth(callback: (List<String>) -> Unit) {
        _isDealsOfMonthLoading.value = true
        repository.getDealsOfMonths().addOnSuccessListener {
            callback(it)
        }.addOnFailureListener {
            callback(listOf())
        }.addOnCompleteListener {
            _isDealsOfMonthLoading.value = false
        }
    }

    fun onTextTyped(text: String) {
        _filteredBaseInfoList.value = HomePageFilterUtils.filterForTypedText(text)
    }

}