package com.example.hungryist.ui.fragment.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hungryist.R
import com.example.hungryist.model.BaseInfoModel
import com.example.hungryist.model.MealFilterModel
import com.example.hungryist.model.MealModel
import com.example.hungryist.model.OpenCloseStatusModel
import com.example.hungryist.model.PlaceFilterModel
import com.example.hungryist.model.SelectStringModel
import com.example.hungryist.repo.BaseRepository
import com.example.hungryist.utils.SharedPreferencesManager
import com.example.hungryist.utils.UserManager
import com.example.hungryist.utils.extension.showToastMessage
import com.example.hungryist.utils.filterutils.FilterableBaseViewModel
import com.example.hungryist.utils.filterutils.MainPageFilterUtils
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: BaseRepository,
    private val sharedPreferencesManager: SharedPreferencesManager,
    val userManager: UserManager,
) : FilterableBaseViewModel() {

    val isRegistered: Boolean
        get():Boolean {
            return sharedPreferencesManager.isRegistered()
        }


    private val _isDealsOfMonthLoading = MutableLiveData(false)
    val isDealsOfMonthLoading: LiveData<Boolean> = _isDealsOfMonthLoading

    private val _isTopPlacesLoading = MutableLiveData(false)
    val isTopPlacesLoading: LiveData<Boolean> = _isTopPlacesLoading

    private val _isPlacesLoading = MutableLiveData(false)
    val isPlacesLoading: LiveData<Boolean> = _isPlacesLoading

    private val _places = MutableLiveData(mutableListOf<SelectStringModel>())
    val places: LiveData<MutableList<SelectStringModel>> = _places

    private var fullList: MutableList<BaseInfoModel> = mutableListOf()

    val isFilterable: Boolean
        get() {
            return filterUtils.filterable()
        }

    val customApplied: Boolean
        get() {
            return filterUtils.isCustomFilterActive()
        }

    private val _filteredBaseInfoList = MutableLiveData<List<BaseInfoModel>>()
    val filteredBaseInfoList: LiveData<List<BaseInfoModel>> = _filteredBaseInfoList

    private val filterUtils by lazy {
        MainPageFilterUtils()
    }

    fun clearFilter() {
        filterUtils.clearFilter()
    }

    fun getBaseList(context: Context) {
        _isTopPlacesLoading.value = true
        repository.getBaseInfoList().addOnSuccessListener {
            getOpenCloseDateList(context, it)
            getMealsList(context, it)
        }.addOnFailureListener {
            initializeFullList(mutableListOf())
        }.addOnCompleteListener {
            _isTopPlacesLoading.value = false
        }

    }

    private fun getMealsList(context: Context, baseInfo: MutableList<BaseInfoModel>) {
        val tasks = mutableListOf<Task<List<MealModel>>>()

        baseInfo.forEach { baseList ->
            val task = repository.getMenuCost(baseList.id)
                .addOnSuccessListener {
                    baseList.meals = it
                }
                .addOnFailureListener {
                    context.showToastMessage(it.message.toString())
                }

            tasks.add(task)
        }

        Tasks.whenAll(tasks)
            .addOnSuccessListener {
                initializeFullList(baseInfo)
            }
            .addOnFailureListener {
                context.showToastMessage(it.message.toString())
            }
    }

    private fun getOpenCloseDateList(context: Context, baseInfo: MutableList<BaseInfoModel>) {
        val tasks = mutableListOf<Task<List<OpenCloseStatusModel>>>()

        baseInfo.forEach { baseList ->
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
                initializeFullList(baseInfo)
            }
            .addOnFailureListener {
                context.showToastMessage(it.message.toString())
            }
    }

    private fun initializeFullList(fullList: MutableList<BaseInfoModel>) {
        this.fullList = fullList
        filterUtils.setBaseInfoList(this.fullList as MutableList<BaseInfoModel>)
        _filteredBaseInfoList.postValue(
            if (isFilterable)
                filterUtils.filterRequest()
            else
                fullList
        )
    }

    fun callPlaces() {
        _isPlacesLoading.value = true
        repository.getPlacesList().addOnSuccessListener {
            _places.value = it.toMutableList()
        }.addOnFailureListener {
            _places.value = mutableListOf()
        }.addOnCompleteListener {
            _isPlacesLoading.value = false
        }

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
        _filteredBaseInfoList.value = filterUtils.filterForTypedText(text)
    }

    fun onFilterSaved(text: String) {
        filterUtils.clearFilter()
        _filteredBaseInfoList.value = filterUtils.filterForTypedText(text)
    }

    fun getSavedList(isFiltered: Boolean): List<BaseInfoModel>? {
        val list = if (isFiltered) filteredBaseInfoList.value else fullList
        return list?.filter {
            userManager.checkSaved(it.id)
        }
    }

    fun getSavedInfoTextResource(context: Context): String {
        val emptySaveInfo = if (sharedPreferencesManager.isRegistered()) {
            R.string.must_be_registered_info
        } else {
            R.string.empty_save_list_info
        }
        return context.getString(emptySaveInfo)
    }

    fun filterPlaces(
        filterItems: PlaceFilterModel,
    ) {

        _filteredBaseInfoList.value = filterUtils.filterForPlaces(filterItems)
    }

    fun filterMeals(filterItems: MealFilterModel) {
        _filteredBaseInfoList.value = filterUtils.filterForMeals(filterItems)
    }

    fun getFilterPlace() = filterUtils.placeFilter
    fun getFilterMeal() = filterUtils.mealFilter
    override fun removeCustomFilter() {
        filterUtils.removeCustomFilter()
        _filteredBaseInfoList.value = fullList
    }

    override fun onTypeSelected(context: Context, name: String) {
        if (name == context.getString(R.string.customFilter))
            _filteredBaseInfoList.value = filterUtils.filterCustom()
        else
            _filteredBaseInfoList.value = filterUtils.filterForCategory(name)
    }

    fun getMealNames(): List<String> {
        return fullList.flatMap {
            it.meals.map {
                it.name
            }
        }
    }

    fun getFullList() = fullList

    fun getFullSavedList() = userManager.savedList

    fun triggerSavedPlace(id:String) {
        userManager.triggerSavedPlace(id)
    }
}