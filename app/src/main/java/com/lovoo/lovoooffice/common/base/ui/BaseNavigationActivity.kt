package com.lovoo.lovoooffice.common.base.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.DrawableRes
import androidx.annotation.NavigationRes
import androidx.annotation.StringRes
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

    open fun backButtonHandling(arguments: Bundle?, toolbar: Toolbar) {
        var shouldShowBackButton = false
        if (arguments != null && arguments.containsKey("shouldShowBackButton")) {
            arguments.getBoolean("shouldShowBackButton").let {
                if(it){
                    toolbar.setNavigationIcon(R.drawable.ic_back_button)
                    toolbar.setNavigationOnClickListener {
                        onBackPressed()
                    }
                }else{
                    toolbar.setNavigationIcon(0)
                    toolbar.setNavigationOnClickListener(null)
                }
            }
        }
    }

    fun setToolbarTitle(title: String) {
        _binding.materialToolbar.title = title
    }

    fun setToolbarTitle(@StringRes titleRes: Int) {
        _binding.materialToolbar.setTitle(titleRes)
    }

    var _showActionButton = true
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_toolbar, menu)
        val shareItem: MenuItem = menu.findItem(R.id.actionButton)

        // show the button when some condition is true
        shareItem.setVisible(_showActionButton)
        return true
    }

    fun setToolbarActionButton(listener: (menuItem : MenuItem, fragmentId : Int)->Unit) {
        _binding.materialToolbar.setOnMenuItemClickListener {
            listener(it, mCurrentDestination)
            true
        }
    }

    fun showActionButton(){
        _showActionButton = true
    }

    fun hideActionButton(){
        _showActionButton = false
    }

    fun showBackButton(){
        _binding.materialToolbar.setNavigationIcon(R.drawable.ic_back_button)
        _binding.materialToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    fun hideBackButton(){
        _binding.materialToolbar.setNavigationIcon(null)
        _binding.materialToolbar.setNavigationOnClickListener(null)
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