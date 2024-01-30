package com.example.hungryist.ui.fragment.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hungryist.R
import com.example.hungryist.model.BaseInfoModel
import com.example.hungryist.model.OpenCloseStatusModel
import com.example.hungryist.model.SelectStringModel
import com.example.hungryist.repo.BaseRepository
import com.example.hungryist.utils.UserManager
import com.example.hungryist.utils.extension.showToastMessage
import com.example.hungryist.utils.filterutils.MainPageFilterUtils
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
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
        _filteredBaseInfoList.value = MainPageFilterUtils.filterForCategory(selectStringModel?.name)
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
        _filteredBaseInfoList.value = MainPageFilterUtils.filterForTypedText(text)
    }

    fun onFilterSaved(text: String) {
        MainPageFilterUtils.resetData()
        _filteredBaseInfoList.value = MainPageFilterUtils.filterForTypedText(text)
    }

    fun getSavedList(isFiltered: Boolean): List<BaseInfoModel>? {
        val list = if (isFiltered) filteredBaseInfoList else baseInfoList
        return list.value?.filter {
            UserManager.checkSaved(it.id)
        }
    }

    fun getSavedInfoTextResource(): String {
        val emptySaveInfo = if (FirebaseAuth.getInstance().uid == null) {
            R.string.must_be_registered_info
        } else {
            R.string.empty_save_list_info
        }
        return context.getString(emptySaveInfo)
    }

}