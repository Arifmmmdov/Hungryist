package com.example.hungryist.ui.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.hungryist.R
import com.example.hungryist.databinding.ActivityEditProfileBinding
import com.example.hungryist.model.ProfileInfoModel
import com.example.hungryist.ui.fragment.profile.ProfileViewModel
import com.example.hungryist.utils.CommonHelper
import com.example.hungryist.utils.DynamicStarFillUtil
import com.example.hungryist.utils.extension.showToastMessage
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

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                viewModel.openGallery(getContentLauncher)
            } else {
                this.showToastMessage(getString(R.string.denied_info))
            }
        }

    private val getContentLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                viewModel.uploadImage(it)
                binding.progressBar.visibility = View.VISIBLE
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListeners()
        setObservers()
        setViews()
    }

    private fun setObservers() {
        viewModel.selectedImageUrl.observe(this) {
            Glide.with(this).load(it)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(binding.profileImage)
            binding.progressBar.visibility = View.GONE
        }
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
            binding.editEmail.setText(info.email)
            binding.editPhoneNumber.setText(info.phoneNumber)
            binding.editName.setText(info.name)
            binding.editSurname.setText(info.surname)
            viewModel.setSelectedImageUrl(info.imageUrl.toString())
        }
    }

    private fun setListeners() {
        binding.saveChanges.setOnClickListener {
            if (checkAllValidities()) {
                viewModel.saveChanges(this, getProfileInfo())
                finish()
            }
        }

        binding.profileImage.setOnClickListener {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
        }
    }

    private fun getProfileInfo(): ProfileInfoModel {
        return viewModel.profileInfo.value?.copy(
            email = binding.editEmail.text.toString(),
            phoneNumber = binding.editPhoneNumber.text.toString(),
            name = binding.editName.text.toString(),
            surname = binding.editSurname.text.toString(),
            imageUrl = viewModel.selectedImageUrl.value
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