package com.lovoo.lovoooffice.presentation.landing

import com.lovoo.lovoooffice.R
import com.lovoo.lovoooffice.common.base.ui.BaseNavigationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingActivity : BaseNavigationActivity() {

    override fun getNavGraph(): Int = R.navigation.nav_graph_landing
}