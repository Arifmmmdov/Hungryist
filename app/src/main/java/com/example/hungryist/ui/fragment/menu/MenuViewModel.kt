package com.example.hungryist.ui.fragment.menu

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hungryist.model.DetailedInfoModel
import com.example.hungryist.model.MenuModel
import com.example.hungryist.repo.Repository
import com.example.hungryist.utils.FilterUtils
import javax.inject.Inject

class MenuViewModel @Inject constructor(val context: Context, val repository: Repository) :
    ViewModel() {
    private val _menuList = MutableLiveData<List<MenuModel>>()
    val menuList: LiveData<List<MenuModel>> = _menuList

    fun getMenuList(id: String) {
        repository.getMenuList(id)
            .addOnSuccessListener {
                _menuList.value = it
                FilterUtils.setList(it)
            }
    }

    fun filterMenu(){

    }
}