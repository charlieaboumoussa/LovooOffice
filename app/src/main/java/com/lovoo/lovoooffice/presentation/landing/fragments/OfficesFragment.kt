package com.lovoo.lovoooffice.presentation.landing.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.lovoo.lovoooffice.common.base.ui.BaseFragment
import com.lovoo.lovoooffice.common.base.ui.BaseNavigationActivity
import com.lovoo.lovoooffice.common.base.viewmodels.BaseViewModel
import com.lovoo.lovoooffice.databinding.FragmentOfficesBinding
import com.lovoo.lovoooffice.presentation.common.navigation.NavigationResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class OfficesFragment : BaseFragment(), NavigationResult {

    private lateinit var _binding: FragmentOfficesBinding
    private val _viewModel: OfficesViewModel by activityViewModels()

    override fun attachViewModel(): BaseViewModel = _viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOfficesBinding.inflate(inflater, container, false)
        _binding.setLifecycleOwner(this)
        _binding.viewModel = _viewModel
        return getBindingPersistentView(_binding.root)
    }

    override fun onViewCreatedBase(view: View, savedInstanceState: Bundle?) {
        if (hasInitializedRootView.compareAndSet(false, true)) {
            setupUI()
            _viewModel.getOffices()
        }
        _viewModel.proceedWithUiAction(NavFlow.SHOW_APPLIED_FILTERS)
    }

    private fun setupUI(){
        observeUiActions()
        setupViewListeners()
    }

    private fun setupViewListeners(){
        _binding.onOfficeClicked = {
            val navAction = OfficesFragmentDirections.actionOfficesFragmentToNavGraphOfficeDetails(it)
            findNavController().navigate(navAction)
        }
        _binding.onLovooFactClicked = {
            val navAction = OfficesFragmentDirections.actionOfficesFragmentToNavGraphLovooFact(it)
            findNavController().navigate(navAction)
        }

        _binding.swipeRefreshLayout.setOnRefreshListener {
            _viewModel.getOffices()
        }

        (requireActivity() as BaseNavigationActivity).setToolbarActionButtonListener{
            _viewModel.proceedWithUiAction(NavFlow.DEFAULT)
            val navAction = OfficesFragmentDirections.actionOfficesFragmentToNavGraphFilters()
            findNavController().navigate(navAction)
        }
    }

    private fun observeUiActions(){
        lifecycleScope.launch {
            _viewModel.uiActions.collectLatest{
                val uiState = it.second
                when(it.first){
                    NavFlow.SHOW_APPLIED_FILTERS,
                    NavFlow.APPLY_FILTERS,
                    NavFlow.CLEAR_FILTERS->{
                        val filtersNumber = uiState.appliedFilters.size
                        if(uiState.appliedFilters.size > 0){
                            (requireActivity() as BaseNavigationActivity).showAndSetFilterNumber(filtersNumber.toString())
                        }else{
                            (requireActivity() as BaseNavigationActivity).hideFilterNumber()
                        }
                    }
                    else->{}
                }
            }
        }
    }

    override fun onNavigationResult(result: Bundle?) {
        _viewModel.filterOffices()
    }
}