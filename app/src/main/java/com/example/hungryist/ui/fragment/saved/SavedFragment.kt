package com.example.hungryist.ui.fragment.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hungryist.adapter.BaseInfoRecyclerAdapter
import com.example.hungryist.databinding.FragmentSavedBinding
import com.example.hungryist.ui.activity.intro.IntroActivity
import com.example.hungryist.ui.fragment.home.HomeViewModel
import com.example.hungryist.utils.UserManager
import com.example.hungryist.utils.extension.triggerVisibility
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SavedFragment : Fragment() {

    private val binding by lazy {
        FragmentSavedBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setListeners()
        setVisibilities()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.filteredBaseInfoList.observe(requireActivity()) {
            setRecyclerAdapter(true)
        }
    }

    private fun setVisibilities() {
        UserManager.savedList.run {
            binding.register.triggerVisibility(FirebaseAuth.getInstance().uid == null)
            binding.login.triggerVisibility(FirebaseAuth.getInstance().uid == null)
            binding.lnrEmptyInfo.triggerVisibility(isEmpty())
            binding.recyclerSaved.triggerVisibility(!isEmpty())
            setViews(this)
        }
    }

    private fun setViews(savedList: MutableList<String>) {
        if (savedList.isEmpty()) {
            binding.emptyInfo.text = viewModel.getSavedInfoTextResource()
        } else {
            setRecyclerAdapter(false)
        }
    }

    private fun setRecyclerAdapter(isFiltered: Boolean) {
        binding.recyclerSaved.apply {
            adapter = viewModel.getSavedList(isFiltered)
                ?.let { BaseInfoRecyclerAdapter(requireContext(), it, viewModel) }
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setListeners() {

        binding.editFilter.addTextChangedListener {
            viewModel.onFilterSaved(it.toString())
        }

        binding.swipeRefresh.setOnRefreshListener {
            binding.editFilter.setText("")
            setViews(UserManager.savedList)
            binding.swipeRefresh.isRefreshing = false
        }

        binding.register.setOnClickListener {
            IntroActivity.intentFor(requireContext(), "register")
        }

        binding.login.setOnClickListener {
            IntroActivity.intentFor(requireContext(), "login")
        }
    }
}