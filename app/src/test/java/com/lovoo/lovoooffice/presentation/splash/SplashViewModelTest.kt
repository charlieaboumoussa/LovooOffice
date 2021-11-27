package com.lovoo.lovoooffice.presentation.splash

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

/*
* Why make the view model test a local test?
*
*  Pure view model tests usually go in the test source set because the view model code
*  shouldn't rely on the Android framework or OS.
* As a local test, it will also run faster because you run the tests on your local machine and not on an emulator or device.
*
* Given When Then Mnemonic
* */

@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class SplashViewModelTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getUiActions() {
    }

    @Test
    fun getOfficeFilters() {
    }
}