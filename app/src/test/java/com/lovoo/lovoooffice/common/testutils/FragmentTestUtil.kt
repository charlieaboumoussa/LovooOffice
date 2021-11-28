package com.lovoo.lovoooffice.common.testutils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation

//TODO[7/5/2021] move to instrumented test to use there
class FragmentTestUtil {
    inline fun <reified F : Fragment> launchFragmentScenario(
            bundle: Bundle?, fragment: F, navController: NavController
    ): FragmentScenario<F> {
        return launchFragmentInContainer(bundle) {
            fragment.also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { lifeCycleOwner ->
                    if (lifeCycleOwner != null) {
                        // The fragment’s view has just been created
                        Navigation.setViewNavController(fragment.requireView(), navController)
                    }
                }
            }
        }
    }
}