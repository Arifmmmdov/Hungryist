package com.example.hungryist.ui.activity.detailedinfo

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hungryist.model.DetailedInfoModel
import com.example.hungryist.repo.Repository
import javax.inject.Inject

class DetailedInfoViewModel @Inject constructor(val context: Context, val repository: Repository) :
    ViewModel() {
    private val _detailedInfo = MutableLiveData<DetailedInfoModel>()
    val detailedInfo: LiveData<DetailedInfoModel> = _detailedInfo

    fun getDetailedInfo(id: String) {
        repository.getDetailedInfo(id)
            .addOnSuccessListener {
                _detailedInfo.value = it
                getOtherDataAsync(id)
            }
            .addOnFailureListener {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            }
    }

    private fun getOtherDataAsync(id: String) {
        repository.getMenuList(id)
            .addOnSuccessListener {
                _detailedInfo.value?.menuList = it
            }

        repository.getReviewsList(id)
            .addOnSuccessListener {
                _detailedInfo.value?.reviewsList = it
            }
    }

    fun savedInfoTrigger() {
        _detailedInfo.value?.apply {
            repository.setDataSaved(referenceId, id, !saved)
            saved = !saved
        }
    }
}