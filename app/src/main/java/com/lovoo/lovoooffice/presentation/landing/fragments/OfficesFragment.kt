package com.lovoo.lovoooffice.presentation.landing.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.lovoo.lovoooffice.R
import com.lovoo.lovoooffice.common.base.ui.BaseFragment
import com.lovoo.lovoooffice.common.base.ui.BaseNavigationActivity
import com.lovoo.lovoooffice.databinding.FragmentOfficesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OfficesFragment : BaseFragment() {

    private lateinit var _binding: FragmentOfficesBinding
    private val _viewModel: OfficesViewModel by navGraphViewModels(R.id.nav_graph_landing, {defaultViewModelProviderFactory})

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (hasInitializedRootView.compareAndSet(false, true)) {
            setupUI()
        }
    }

    private fun setupUI(){
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
            _binding.swipeRefreshLayout.isRefreshing = false
            _viewModel.getOffices()
        }

        (requireActivity() as BaseNavigationActivity).setToolbarActionButton({menuItem, fragmentId->
            when(menuItem.itemId){
                R.id.actionButton->{
                    menuItem.setIcon(R.drawable.ic_carousel_indicator_enabled)
                    val navAction = OfficesFragmentDirections.actionOfficesFragmentToNavGraphFilters()
                    findNavController().navigate(navAction)
                }
                else->{
                    menuItem.setIcon(null)
                }
            }
        })
    }

    private fun observeUiActions(){
        lifecycleScope.launch {
            _viewModel.uiActions.collect {
                val uiState = it.second
                when(it.first){

                }
            }
        }
    }
}