package com.lovoo.lovoooffice.presentation.officebooking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.lovoo.lovoooffice.R
import com.lovoo.lovoooffice.common.base.ui.BaseFragment
import com.lovoo.lovoooffice.common.base.viewmodels.BaseViewModel
import com.lovoo.lovoooffice.databinding.FragmentOfficeBookingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class OfficeBookingFragment : BaseFragment() {

    private lateinit var _binding: FragmentOfficeBookingBinding
    private val _viewModel: OfficeBookingViewModel by viewModels()
    private val _navArgs: OfficeBookingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOfficeBookingBinding.inflate(inflater, container, false)
        _binding.setLifecycleOwner(this)
        _viewModel.setOfficeToBeBooked(_navArgs.office)
        _binding.viewModel = _viewModel
        return _binding.root
    }

    override fun onViewCreatedBase(view: View, savedInstanceState: Bundle?) {
        setEditTextListeners()
        observeUiActions()
        _viewModel.getOfficeBookingsByOfficeId()
        _binding.materialButtonBookOffice.setOnClickListener{
            _viewModel.proceedWithUiAction(NavFlow.BOOK_OFFICE)
        }
    }

    private fun setEditTextListeners() {
        _binding.dropdownStartDate.setOnClickListener {
            _viewModel.proceedWithUiAction(NavFlow.DATE_PICKER)
        }
        _binding.dropdownStartTime.setOnClickListener {
            _viewModel.proceedWithUiAction(NavFlow.START_TIME_PICKER)
        }
        _binding.dropdownEndTime.setOnClickListener {
            _viewModel.proceedWithUiAction(NavFlow.END_TIME_PICKER)
        }
    }

    private fun observeUiActions(){
        lifecycleScope.launch {
            _viewModel.uiActions.collect{
                val uiState = it.second
                when(it.first){
                    NavFlow.DATE_PICKER->{
                        val datePickerBuilder = MaterialDatePicker.Builder.datePicker()
                        val constraintsBuilder = CalendarConstraints.Builder()
                            .setValidator(DateValidatorPointForward.now())
                        uiState.startDateLong?.let {
                            datePickerBuilder.setSelection(it)
                            constraintsBuilder.setStart(it)
                        }
                        datePickerBuilder.setCalendarConstraints(constraintsBuilder.build())
                        val datePicker = datePickerBuilder.build()
                        datePicker.addOnPositiveButtonClickListener {
                            _viewModel.setStartDate(it)
                            _binding.dropdownStartDate.setText(uiState.getFormattedDate(it))
                        }
                        datePicker.show(childFragmentManager, "tag")
                    }
                    NavFlow.START_TIME_PICKER,
                    NavFlow.END_TIME_PICKER->{
                        val timePickerBuilder = MaterialTimePicker.Builder()
                            .setTimeFormat(TimeFormat.CLOCK_12H)
                        when(it.first){
                            NavFlow.START_TIME_PICKER->{
                                uiState.startTimeLong?.let {
//                                    timePickerBuilder.setHour(((it / (1000 * 60 * 60)) % 24).toInt())
//                                    timePickerBuilder.setMinute(((it / (1000*60)) % 60).toInt())
                                }
                                val timePicker = timePickerBuilder.build()
                                timePicker.addOnPositiveButtonClickListener {
                                    val newHour: Int = timePicker.hour
                                    val newMinute: Int = timePicker.minute
                                    val calendar = Calendar.getInstance()
                                    calendar.set(Calendar.HOUR_OF_DAY, newHour)
                                    calendar.set(Calendar.MINUTE, newMinute)
                                    _viewModel.setStartTime(calendar.timeInMillis)
                                    _binding.dropdownStartTime.setText(uiState.getFormattedTime(calendar.timeInMillis))
                                }
                                timePicker.show(childFragmentManager, "tag")
                            }
                            NavFlow.END_TIME_PICKER->{
                                uiState.endTimeLong?.let {
//                                    timePickerBuilder.setHour(((it / (1000 * 60 * 60)) % 24).toInt())
//                                    timePickerBuilder.setMinute(((it / (1000*60)) % 60).toInt())
                                }
                                val timePicker = timePickerBuilder.build()
                                timePicker.addOnPositiveButtonClickListener {
                                    val newHour: Int = timePicker.hour
                                    val newMinute: Int = timePicker.minute
                                    val calendar = Calendar.getInstance()
                                    calendar.set(Calendar.HOUR_OF_DAY, newHour)
                                    calendar.set(Calendar.MINUTE, newMinute)
                                    _viewModel.setEndTime(calendar.timeInMillis)
                                    _binding.dropdownEndTime.setText(uiState.getFormattedTime(calendar.timeInMillis))
                                }
                                timePicker.show(childFragmentManager, "tag")
                            }
                            else->{}
                        }
                    }
                    NavFlow.BOOKING_DONE->{
                        findNavController().popBackStack()
                    }
                    NavFlow.BOOKING_ERROR->{
                        if(uiState.areStartEndTimeValid.not()){
                            _binding.textInputLayoutStartTime.error = getString(R.string.invalid_dates)
                        }else if(uiState.isConflictWithOtherBooking){
                            _binding.textInputLayoutStartTime.error = getString(R.string.office_already_booked)
                        }else{
                            showError(getString(R.string.error_failed_book_office))
                        }
                    }
                    else->{}
                }
            }
        }
    }

    override fun attachViewModel(): BaseViewModel? = null
}