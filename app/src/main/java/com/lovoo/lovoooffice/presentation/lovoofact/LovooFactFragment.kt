package com.lovoo.lovoooffice.presentation.lovoofact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.navArgs
import com.lovoo.lovoooffice.common.base.ui.BaseFragment
import com.lovoo.lovoooffice.common.base.viewmodels.BaseViewModel
import com.lovoo.lovoooffice.databinding.FragmentLovooFactBinding
import com.lovoo.lovoooffice.databinding.FragmentOfficesBinding
import com.lovoo.lovoooffice.presentation.officedetails.OfficeDetailsFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LovooFactFragment : BaseFragment() {

    private lateinit var _binding: FragmentLovooFactBinding
    private val _navArgs: LovooFactFragmentArgs by navArgs()

    override fun attachViewModel(): BaseViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLovooFactBinding.inflate(inflater, container, false)
        _binding.setLifecycleOwner(this)
        _binding.lovooFact = _navArgs.lovooFact
        return _binding.root
    }

    override fun onViewCreatedBase(view: View, savedInstanceState: Bundle?) {

    }
}