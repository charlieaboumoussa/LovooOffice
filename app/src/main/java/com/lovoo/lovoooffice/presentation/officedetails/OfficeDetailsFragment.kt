package com.lovoo.lovoooffice.presentation.officedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lovoo.lovoooffice.common.base.ui.BaseFragment
import com.lovoo.lovoooffice.databinding.FragmentOfficeDetailsBinding
import com.lovoo.lovoooffice.presentation.landing.fragments.OfficesFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfficeDetailsFragment : BaseFragment() {

    private lateinit var _binding: FragmentOfficeDetailsBinding
    private val _viewModel: OfficeDetailsViewModel by viewModels()
    private val _navArgs: OfficeDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOfficeDetailsBinding.inflate(inflater, container, false)
        _binding.setLifecycleOwner(this)
        _binding.viewModel = _viewModel
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _viewModel.setOfficeDetails(_navArgs.office)
        _binding.materialButtonBookOffice.setOnClickListener {
            val navAction = OfficeDetailsFragmentDirections.actionOfficeDetailsFragmentToNavGraphOfficeBooking()
            findNavController().navigate(navAction)
        }
    }
}