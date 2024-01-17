package com.example.hungryist.ui.activity.detailedinfo

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hungryist.model.DetailedInfoModel
import com.example.hungryist.model.OpenCloseTimes
import com.example.hungryist.model.RatingModel
import com.example.hungryist.repo.Repository
import javax.inject.Inject

class DetailedInfoViewModel @Inject constructor(val context: Context, val repository: Repository) :
    ViewModel() {
    private val _detailedInfo = MutableLiveData<DetailedInfoModel>()
    val detailedInfo: LiveData<DetailedInfoModel> = _detailedInfo
    private val _ratingList = MutableLiveData<List<RatingModel>>()
    val ratingList: LiveData<List<RatingModel>> = _ratingList
    private val _openClosedDateList = MutableLiveData<List<OpenCloseTimes>>()
    val openClosedDateList: LiveData<List<OpenCloseTimes>> = _openClosedDateList

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

        repository.getOpenCloseDate(id)
            .addOnSuccessListener {
                Log.d("TestResult", "getOtherDataAsync: $it")
                _openClosedDateList.value = it
            }

        repository.getRatingList(id)
            .addOnSuccessListener {
                Log.d("TestResult", "getOtherDataAsync: $it")
                _ratingList.value = it
            }
    }

    fun savedInfoTrigger() {
        _detailedInfo.value?.apply {
            repository.setDataSaved(referenceId, id, !saved)
            saved = !saved
        }
    }
}