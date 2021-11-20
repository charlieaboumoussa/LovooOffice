package com.lovoo.lovoooffice.presentation.landing.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import com.lovoo.lovoooffice.common.base.ui.BaseFragment
import com.lovoo.lovoooffice.databinding.FragmentOfficesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfficesFragment : BaseFragment() {

    private lateinit var _binding: FragmentOfficesBinding
    private val _viewModel: OfficesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOfficesBinding.inflate(inflater, container, false)
        _binding.setLifecycleOwner(this)
//        _binding.viewModel = _viewModel
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding.switchTheme.setOnCheckedChangeListener { compoundButton, isChecked ->
            when(isChecked){
                true->{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)}
                false->{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
    }
}