package com.lovoo.lovoooffice.presentation.landing.fragments.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import com.lovoo.lovoooffice.common.base.ui.BaseFragment
import com.lovoo.lovoooffice.databinding.FragmentOfficeFiltersBinding
import com.lovoo.lovoooffice.databinding.FragmentOfficesBinding
import com.lovoo.lovoooffice.presentation.landing.fragments.OfficesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfficeFiltersFragment : BaseFragment() {

    private lateinit var _binding: FragmentOfficeFiltersBinding
    private val _viewModel: OfficesViewModel by viewModels(
        ownerProducer = {requireActivity()}
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOfficeFiltersBinding.inflate(inflater, container, false)
        _binding.setLifecycleOwner(this)
        _binding.viewModel = _viewModel
        return _binding.root
    }
}