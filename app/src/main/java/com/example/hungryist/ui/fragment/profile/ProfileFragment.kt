package com.example.hungryist.ui.fragment.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.hungryist.R
import com.example.hungryist.databinding.FragmentProfileBinding
import com.example.hungryist.model.ProfileInfoModel
import com.example.hungryist.ui.activity.EditProfileActivity
import com.example.hungryist.ui.activity.intro.IntroActivity
import com.example.hungryist.utils.DynamicStarFillUtil
import com.example.hungryist.utils.enum.VisibleStatusEnum
import com.example.hungryist.utils.extension.triggerVisibility
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val binding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }

    private val starFillUtil by lazy {
        DynamicStarFillUtil(binding.root)
    }

    @Inject
    lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setListeners()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.profileInfo.observe(requireActivity()) {
            binding.constraintEmptyInfo.triggerVisibility(it == null)
            binding.profileView.triggerVisibility(it != null)
            if (it != null)
                setViews(it)
        }
    }

    private fun setViews(info: ProfileInfoModel) {
        starFillUtil.fillStars(info.rate ?: 0.0, true)
        binding.name.text = getString(R.string.name_surname, info.name, info.surname)
        binding.reviews.text = getString(R.string.reviews, info.reviews ?: 0)
        Glide.with(requireContext()).load(info.imageUrl)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(binding.mainImage)
    }

    private fun setListeners() {
        binding.logOut.setOnClickListener {
            viewModel.logOut(requireActivity())
        }

        binding.editProfile.setOnClickListener {
            EditProfileActivity.intentFor(requireContext())
        }

        binding.register.setOnClickListener {
            IntroActivity.intentFor(requireContext(),"register")
        }

        binding.login.setOnClickListener {
            IntroActivity.intentFor(requireContext(),"login")
        }
    }


}