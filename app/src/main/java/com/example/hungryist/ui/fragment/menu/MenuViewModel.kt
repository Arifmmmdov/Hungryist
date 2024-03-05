package com.example.hungryist.ui.fragment.menu

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hungryist.model.MenuModel
import com.example.hungryist.model.SelectStringModel
import com.example.hungryist.repo.DetailedInfoRepository
import com.example.hungryist.utils.filterutils.FilterableBaseViewModel
import com.example.hungryist.utils.filterutils.MenuFilterUtils
import javax.inject.Inject

class MenuViewModel @Inject constructor(
    val repository: DetailedInfoRepository,
) :
    FilterableBaseViewModel() {
    private val _menuList = MutableLiveData<List<MenuModel>>()
    val menuList: LiveData<List<MenuModel>> = _menuList
    private val _categoriesList = MutableLiveData<List<SelectStringModel>>()
    val categoriesList: LiveData<List<SelectStringModel>> = _categoriesList

    private val menuFilterUtils by lazy {
        MenuFilterUtils()
    }

    fun getMenuList() {
        repository.getMenuList()
            .addOnSuccessListener {
                _menuList.value = it
                menuFilterUtils.setMenuList(it)
            }
            .addOnFailureListener {
                _menuList.value = listOf()
                menuFilterUtils.setMenuList(listOf())
            }
    }

    private fun getCategoriesList() {
        repository.getMenuCategoriesList()
            .addOnSuccessListener {
                _categoriesList.value = it
            }
    }

    fun getData() {
        getCategoriesList()
        getMenuList()
    }

    fun filterForTyped(typed: String?) {
        _menuList.value = menuFilterUtils.filterForTypedText(typed)
    }

    override fun onTypeSelected(context: Context,name: String) {
        _menuList.value = menuFilterUtils.filterForCategory(name)
    }

    override fun removeCustomFilter() {}
}