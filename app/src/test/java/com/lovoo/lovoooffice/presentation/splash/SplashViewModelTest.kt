package com.lovoo.lovoooffice.presentation.splash

import com.lovoo.lovoooffice.core.domain.repositories.OfficeRepository
import com.lovoo.lovoooffice.core.domain.usecases.office.GetOfficeFiltersUseCase
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import javax.inject.Inject

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

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var useCase: GetOfficeFiltersUseCase

    @BindValue
    var _viewModel : SplashViewModel? = null

    @Before
    fun setUp() {
        hiltRule.inject()
        _viewModel = SplashViewModel(useCase)
    }

    @After
    fun tearDown() {
        _viewModel = null
    }

    @Test
    fun getOfficeFilters_noInput_officeFiltersList() {
        //GIVEN app starts

        //WHEN get office filters is launched
        _viewModel?.getOfficeFilters()

        //THEN app should start
        val uiActions = _viewModel?.uiActions
        assertThat(uiActions?.value?.first, `is`(NavFlow.START_APP))
    }
}