package com.lovoo.lovoooffice.presentation.officefilters

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import com.lovoo.lovoooffice.R
import com.lovoo.lovoooffice.common.base.ui.BaseFragment
import com.lovoo.lovoooffice.common.base.ui.BaseNavigationActivity
import com.lovoo.lovoooffice.common.base.viewmodels.BaseViewModel
import com.lovoo.lovoooffice.core.domain.model.Filter
import com.lovoo.lovoooffice.databinding.FragmentOfficeFiltersBinding
import com.lovoo.lovoooffice.presentation.landing.fragments.NavFlow
import com.lovoo.lovoooffice.presentation.landing.fragments.OfficesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OfficeFiltersFragment : BaseFragment() {

    private lateinit var _binding: FragmentOfficeFiltersBinding
    private val _viewModel: OfficesViewModel by activityViewModels()

    override fun attachViewModel(): BaseViewModel = _viewModel

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

    override fun onViewCreatedBase(view: View, savedInstanceState: Bundle?) {
        setEditTextsListener()
        observeUiActions()
        _viewModel.getFilters()
    }

    private fun observeUiActions(){
        lifecycleScope.launch {
            _viewModel.uiActions.collect {
                when(it.first){
                    NavFlow.APPLY_FILTERS->{
                        (requireActivity() as BaseNavigationActivity).navigateBackWithResult()
                    }
                    NavFlow.CLEAR_FILTERS->{
                        (requireActivity() as BaseNavigationActivity).navigateBackWithResult()
                    }
                    else->{}
                }
            }
        }
    }

    private fun setEditTextsListener() {
        _binding.dropdownType.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                text?.takeIf { it.isNotEmpty() }?.let {
                    _viewModel.addFilter(Filter(it.toString(), OfficesViewModel.FilterType.TYPE.name.lowercase()))
                }
            }
        })
        _binding.dropdownDepartment.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                text?.takeIf { it.isNotEmpty() }?.let {
                    _viewModel.addFilter(Filter(it.toString(), OfficesViewModel.FilterType.DEPARTMENT.name.lowercase()))
                }
            }
        })
    }
}