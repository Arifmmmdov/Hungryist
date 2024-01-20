package com.example.hungryist.utils.filterutils

import com.example.hungryist.model.MenuModel

object MenuFilterUtils : BaseFilterUtils<MenuModel>() {


    fun setMenuList(list: List<MenuModel>) {
        this.list = list
        category = ""
        typed = ""
    }

    override fun filter(): List<MenuModel> {
        return list.filter {
            (category.isNullOrBlank() || category == it.type) &&
                    (typed.isNullOrBlank() ||
                            it.name.lowercase().contains(typed!!.lowercase()) ||
                            it.description.lowercase().contains(typed!!.lowercase())
                            )
        }
    }
}