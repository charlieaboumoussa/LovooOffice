package com.lovoo.lovoooffice.presentation.officebooking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.lovoo.lovoooffice.common.base.ui.BaseFragment
import com.lovoo.lovoooffice.databinding.FragmentLovooFactBinding
import com.lovoo.lovoooffice.databinding.FragmentOfficesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfficeBookingFragment : BaseFragment() {

    private lateinit var _binding: FragmentLovooFactBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLovooFactBinding.inflate(inflater, container, false)
        _binding.setLifecycleOwner(this)
//        _binding.viewModel = _viewModel
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}