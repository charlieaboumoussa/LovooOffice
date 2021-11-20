package com.lovoo.lovoooffice.common.base.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.NavigationRes
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.lovoo.lovoooffice.R
import com.lovoo.lovoooffice.databinding.ActivityBaseNavigationBinding

abstract class BaseNavigationActivity : BaseActivity() {

    private lateinit var _binding: ActivityBaseNavigationBinding

    private lateinit var mNavGraph: NavGraph
    private lateinit var mNavController: NavController
    private lateinit var mNavHostFragment: NavHostFragment
    private var mCurrentDestination: Int = 0

    private val mNavigationListener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
        mCurrentDestination = destination.id
        titleHandling(arguments, _binding.materialToolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBaseNavigationBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        mNavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        mNavController = mNavHostFragment.navController
        mNavGraph = mNavController.navInflater.inflate(getNavGraph())
        if (getStartDestinationBundle() != null) {
            getNavController().setGraph(mNavGraph, getStartDestinationBundle()!!)
        } else {
            getNavController().graph = mNavGraph
        }
    }

    @NavigationRes
    abstract fun getNavGraph(): Int

    open fun getStartDestinationBundle(): Bundle? {
        return null
    }

    open fun getStartDestination(): Int? {
        return null
    }

    override fun onResume() {
        super.onResume()
        mNavController.addOnDestinationChangedListener(mNavigationListener)
    }

    override fun onPause() {
        mNavController.removeOnDestinationChangedListener(mNavigationListener)
        super.onPause()
    }

    fun getNavController(): NavController {
        return mNavController
    }

    fun getNavHostFragment(): NavHostFragment {
        return mNavHostFragment
    }

    open fun customNavGraphConfig(navGraph: NavGraph): Boolean {
        return false
    }

    open fun shouldShowAMBHeader(): Boolean {
        return true
    }

    open fun titleHandling(arguments: Bundle?, toolbar: Toolbar) {
        var title = ""
        if (arguments != null && arguments.containsKey("title")) {
            arguments.getInt("title").let { strRes ->
                title = getString(strRes)
            }
        }
        if(title.isNotEmpty()) {
            toolbar.setTitle(title)
        }
    }

    fun setTitle(title: String) {
        _binding.materialToolbar.title = title
    }

    fun navigateTo(action: Int) {
        mNavController.navigate(action)
    }

    fun navigateTo(action: Int, bundle: Bundle) {
        mNavController.navigate(action, bundle)
    }

    fun getCurrentDestination(): Int {
        return mCurrentDestination
    }
}