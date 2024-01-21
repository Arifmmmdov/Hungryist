package com.example.hungryist.ui.fragment.reviews

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hungryist.model.ReviewsModel
import com.example.hungryist.repo.DetailedInfoRepository
import javax.inject.Inject

class ReviewsViewModel @Inject constructor(
    val context: Context,
    val repository: DetailedInfoRepository,
) : ViewModel() {

    private val _reviewsList = MutableLiveData<List<ReviewsModel>>()
    val reviewsList: LiveData<List<ReviewsModel>> = _reviewsList

    fun getReviews() {
        repository.getReviewsList()
            .addOnSuccessListener {
                _reviewsList.value = it
            }
            .addOnFailureListener {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            }
    }
}