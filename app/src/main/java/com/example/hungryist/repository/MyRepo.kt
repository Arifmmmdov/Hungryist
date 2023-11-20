package com.example.hungryist.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MyRepo {

    object Singleton{
        var instance = MyRepo()
    }

    fun getLiveData(): LiveData<List<Int>> {
        val list = listOf<Int>(1, 2, 3, 3, 4, 2, 32, 1, 2)
        return MutableLiveData(list)
    }
}