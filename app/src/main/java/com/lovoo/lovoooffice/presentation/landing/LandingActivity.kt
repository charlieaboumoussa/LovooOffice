package com.lovoo.lovoooffice.presentation.landing

import android.os.Bundle
import com.lovoo.lovoooffice.R
import com.lovoo.lovoooffice.common.base.ui.BaseNavigationActivity
import com.lovoo.lovoooffice.common.base.viewmodels.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingActivity : BaseNavigationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getNavController().addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id){
                R.id.officesFragment->{
                    setToolbarTitle(getString(R.string.offices))
                    hideBackButton()
                    showActionButton()
                }
                else->{
                    showBackButton()
                    hideActionButton()
                    hideFilterNumber()
                }
            }
        }
    }

    override fun getNavGraph(): Int = R.navigation.nav_graph_landing

    override fun attachViewModel(): BaseViewModel? = null
}