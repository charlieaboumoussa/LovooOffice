package com.lovoo.lovoooffice.common.base.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.NavigationRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.lovoo.lovoooffice.R
import com.lovoo.lovoooffice.databinding.ActivityBaseNavigationBinding
import com.lovoo.lovoooffice.presentation.common.navigation.NavigationResult
import kotlin.properties.Delegates

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

    fun navigateBackWithResult(result: Bundle? = null, destinationId : Int? = null, inclusive : Boolean = false) {
        val childFragmentManager = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.childFragmentManager
        var backStackListener: FragmentManager.OnBackStackChangedListener by Delegates.notNull()
        backStackListener = FragmentManager.OnBackStackChangedListener {
            (childFragmentManager?.fragments?.get(0) as NavigationResult).onNavigationResult(result)
            childFragmentManager.removeOnBackStackChangedListener(backStackListener)
        }
        childFragmentManager?.addOnBackStackChangedListener(backStackListener)
        if(destinationId != null){
            getNavController().popBackStack(destinationId, inclusive)
        }else{
            getNavController().popBackStack()
        }
    }

    fun setToolbarTitle(title: String) {
        _binding.materialToolbar.title = title
    }

    fun setToolbarTitle(@StringRes titleRes: Int) {
        _binding.materialToolbar.setTitle(titleRes)
    }

    fun setToolbarActionButtonListener(listener: () ->Unit) {
        _binding.action.setOnClickListener {
            listener()
        }
    }

    fun setToolbarActionIcon(@DrawableRes drawableRes: Int) {
        _binding.action.setImageResource(drawableRes)
    }

    fun showActionButton(){
        _binding.action.visibility = View.VISIBLE
    }

    fun showAndSetFilterNumber(filterNumber : String){
        _binding.cardViewFilterNumber.visibility = View.VISIBLE
        _binding.textViewFilterNumber.text = filterNumber
    }

    fun hideFilterNumber(){
        _binding.cardViewFilterNumber.visibility = View.GONE
    }

    fun hideActionButton(){
        _binding.action.visibility = View.GONE
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