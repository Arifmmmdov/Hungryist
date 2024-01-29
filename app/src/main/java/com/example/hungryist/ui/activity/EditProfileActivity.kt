package com.example.hungryist.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.hungryist.R
import com.example.hungryist.databinding.ActivityEditProfileBinding
import com.example.hungryist.model.ProfileInfoModel
import com.example.hungryist.ui.fragment.profile.ProfileViewModel
import com.example.hungryist.utils.CommonHelper
import com.example.hungryist.utils.DynamicStarFillUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityEditProfileBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModel: ProfileViewModel

    private val starFillUtil by lazy {
        DynamicStarFillUtil(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListeners()
        setViews()
    }

    private fun setViews() {
        viewModel.profileInfo.value?.let { info ->
            info.rate?.let { starFillUtil.fillStars(it, true) }
            binding.name.text = getString(
                R.string.name_surname,
                info.name,
                info.surname
            )
            binding.reviews.text =
                getString(R.string.reviews, info.reviews.toString())
            Glide.with(this).load(info.imageUrl).into(binding.profileImage)
            binding.editEmail.setText(info.email)
            binding.editPhoneNumber.setText(info.phoneNumber)
            binding.editName.setText(info.name)
            binding.editSurname.setText(info.surname)
        }
    }

    private fun setListeners() {
        binding.saveChanges.setOnClickListener {
            if (checkAllValidities()) {
                viewModel.saveChanges(this, getProfileInfo())
            }
        }
    }

    private fun getProfileInfo(): ProfileInfoModel {
        return viewModel.profileInfo.value?.copy(
            email = binding.editEmail.text.toString(),
            phoneNumber = binding.editPhoneNumber.text.toString(),
            name = binding.editName.text.toString(),
            surname = binding.editSurname.text.toString()
        )!!
    }

    private fun checkAllValidities(): Boolean {
        val fieldsToCheck = listOf(
            Pair(binding.editEmail, R.string.e_mail_error),
            Pair(binding.editPhoneNumber, R.string.phone_number_error)
        )

        fieldsToCheck.forEach { (editText, errorMessageResId) ->
            val value = editText.text.toString()
            val isValid = when (editText) {
                binding.editEmail -> CommonHelper.isValidEmail(value) == true || value.isEmpty()
                binding.editPhoneNumber -> CommonHelper.isValidPhoneNumber(value) || value.isEmpty()
                else -> true
            }

            if (!isValid) {
                editText.error = getString(errorMessageResId)
                return false
            }
            editText.error = null
        }

        return true
    }

    companion object {
        fun intentFor(context: Context) {
            context.startActivity(Intent(context, EditProfileActivity::class.java))
        }
    }
}