package com.lovoo.lovoooffice.presentation.officebooking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lovoo.lovoooffice.common.base.state.Resource
import com.lovoo.lovoooffice.common.base.viewmodels.BaseViewModel
import com.lovoo.lovoooffice.core.domain.model.Office
import com.lovoo.lovoooffice.core.domain.model.OfficeBooking
import com.lovoo.lovoooffice.core.domain.usecases.office.GetOfficeBookingsDatabaseUseCase
import com.lovoo.lovoooffice.core.domain.usecases.office.OfficeBookingsDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject


enum class NavFlow{
    DEFAULT,
    BOOK_OFFICE,
    BOOKING_DONE,
    BOOKING_ERROR,
    DATE_PICKER,
    START_TIME_PICKER,
    END_TIME_PICKER,
}

@HiltViewModel
class OfficeBookingViewModel @Inject constructor(
    private val _getOfficeBookingDatabaseUseCase: GetOfficeBookingsDatabaseUseCase,
    private val _officeBookingDatabaseUseCase: OfficeBookingsDatabaseUseCase,
) : BaseViewModel() {

    private val _uiState = MutableLiveData(UIState())
    val uiState: LiveData<UIState> = _uiState

    private val _uiActions = MutableSharedFlow<Pair<NavFlow, UIState>>(0)
    val uiActions: SharedFlow<Pair<NavFlow, UIState>> = _uiActions

    inner class UIState{
        var office : Office? = null
        var bookings : List<OfficeBooking>? = null
        var reason : String? = null

        var startDateLong : Long? = null
        var startTimeLong : Long? = null
        var endTimeLong : Long? = null
        var areStartEndTimeValid : Boolean = false
        var isConflictWithOtherBooking : Boolean = false
        val simpleDateFormat : SimpleDateFormat = SimpleDateFormat("MMM dd yyyy", Locale.getDefault())
        val simpleTimeFormat : SimpleDateFormat = SimpleDateFormat("hh:mm aa", Locale.getDefault())

        fun getFormattedDate(dateLong : Long) : String{
            return  simpleDateFormat.format(dateLong)
        }

        fun getFormattedTime(timeLong : Long) : String{
            return  simpleTimeFormat.format(timeLong)
        }
    }

    init {
        setStartDate(Calendar.getInstance().timeInMillis)
    }

    fun setOfficeToBeBooked(office : Office){
        _uiState.value = _uiState.value?.apply {
            this.office = office
        }
    }

    fun setStartDate(dateLong : Long){
        _uiState.value = _uiState.value?.apply {
            this.startDateLong = dateLong
        }
    }

    fun setStartTime(timeLong : Long){
        _uiState.value = _uiState.value?.apply {
            this.startTimeLong = timeLong
        }
    }

    fun setEndTime(timeLong : Long){
        _uiState.value = _uiState.value?.apply {
            this.endTimeLong = timeLong
        }
    }

    fun getOfficeBookingsByOfficeId(){
        viewModelScope.launch {
            _uiState.value?.let {uiState->
                uiState.office?.let {
                    val officeBooking = OfficeBooking()
                    officeBooking.officeId = it.id
                    _getOfficeBookingDatabaseUseCase(
                        GetOfficeBookingsDatabaseUseCase.GetBookingsDatabaseOperation.GetFlowById(officeBooking)).collect {
                        uiState.bookings = it.data ?: emptyList()
                        _uiState.value = uiState
                    }
                }
            }
        }
    }

    private fun isBookingConflicted(officeBooking: OfficeBooking) : Boolean{
        _uiState.value?.bookings?.forEach { anyPreviousBooking->
            if(((isTimeGreater(officeBooking.startTime, anyPreviousBooking.startTime) && isTimeLess(officeBooking.endTime, anyPreviousBooking.endTime))
                    || (isTimeLess(officeBooking.startTime, anyPreviousBooking.endTime) && isTimeGreater(officeBooking.endTime, anyPreviousBooking.endTime))
                    || (isTimeLess(officeBooking.startTime, anyPreviousBooking.startTime) && isTimeGreater(officeBooking.endTime, anyPreviousBooking.startTime)))
                    && isSameDay(officeBooking.startDate, anyPreviousBooking.startDate)){
                return true
            }
        }
        return false
    }

    private fun isSameDay(firstDate: Long, secondDate: Long) : Boolean{
        val firstCalendar = Calendar.getInstance()
        val secondCalendar = Calendar.getInstance()
        firstCalendar.time = Date(firstDate)
        secondCalendar.time = Date(secondDate)
        return firstCalendar[Calendar.DAY_OF_YEAR] == secondCalendar[Calendar.DAY_OF_YEAR] &&
                firstCalendar[Calendar.YEAR] == secondCalendar[Calendar.YEAR]
    }

//    private fun isTimeGreater(firstDate: Long, secondDate: Long) : Boolean{
//        val firstCalendar = Calendar.getInstance()
//        val secondCalendar = Calendar.getInstance()
//        firstCalendar.time = Date(firstDate)
//        secondCalendar.time = Date(secondDate)
//        if(firstCalendar.time.after(secondCalendar.time)) {
//            return true
//        }
//        return false
//    }

    private fun isTimeGreater(firstDate: Long, secondDate: Long) : Boolean{
        try {
            val simpleDateFormat = SimpleDateFormat("hh:mm:ss aa", Locale.getDefault())
            val time1 = simpleDateFormat.parse(simpleDateFormat.format(firstDate))
            val calendar1 = Calendar.getInstance()
            calendar1.time = time1
            calendar1.add(Calendar.DATE, 1)
            val time2 = simpleDateFormat.parse(simpleDateFormat.format(secondDate))
            val calendar2 = Calendar.getInstance()
            calendar2.time = time2
            calendar2.add(Calendar.DATE, 1)
            if (calendar1.time.after(calendar2.time)) {
                return true
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return false
    }

    private fun isTimeLess(firstDate: Long, secondDate: Long) : Boolean{
        try {
            val simpleDateFormat = SimpleDateFormat("hh:mm:ss aa", Locale.getDefault())
            val time1 = simpleDateFormat.parse(simpleDateFormat.format(firstDate))
            val calendar1 = Calendar.getInstance()
            calendar1.time = time1
            calendar1.add(Calendar.DATE, 1)
            val time2 = simpleDateFormat.parse(simpleDateFormat.format(secondDate))
            val calendar2 = Calendar.getInstance()
            calendar2.time = time2
            calendar2.add(Calendar.DATE, 1)
            if (calendar1.time.before(calendar2.time)) {
                return true
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return false
    }

//    private fun isTimeLess(firstDate: Long, secondDate: Long) : Boolean{
//        val firstCalendar = Calendar.getInstance()
//        val secondCalendar = Calendar.getInstance()
//        firstCalendar.time = Date(firstDate)
//        secondCalendar.time = Date(secondDate)
//        if(firstCalendar.time.before(secondCalendar.time)) {
//            return true
//        }
//        return false
//    }

    fun proceedWithUiAction(action : NavFlow){
        viewModelScope.launch {
            _uiState.value?.let { uiState->
                when(action){
                    NavFlow.BOOK_OFFICE->{
                        val officeBooking = OfficeBooking()
                        uiState.office?.let {
                            officeBooking.officeId = it.id
                        }
                        uiState.reason?.let {
                            officeBooking.reason = it
                        }
                        uiState.startDateLong?.let {
                            officeBooking.startDate = it
                        }
                        uiState.startTimeLong?.let {
                            officeBooking.startTime =it
                        }
                        uiState.endTimeLong?.let {
                            officeBooking.endTime = it
                        }

                        if(uiState.reason.isNullOrEmpty().not() &&
                            checkIfLongIsNullOrZero(uiState.startDateLong).not() &&
                            checkIfLongIsNullOrZero(uiState.startTimeLong).not() &&
                            checkIfLongIsNullOrZero(uiState.endTimeLong).not()){
                            uiState.areStartEndTimeValid = isTimeGreater(uiState.startTimeLong!!, uiState.endTimeLong!!).not()
                            uiState.isConflictWithOtherBooking = isBookingConflicted(officeBooking)
                            if(uiState.areStartEndTimeValid && uiState.isConflictWithOtherBooking.not()){
                                _officeBookingDatabaseUseCase(OfficeBookingsDatabaseUseCase.BookingsDatabaseOperation.Insert(officeBooking)).collect {
                                    if(it is Resource.Success){
                                        _uiActions.emit(Pair(NavFlow.BOOKING_DONE, uiState))
                                    }else{
                                        _uiActions.emit(Pair(NavFlow.BOOKING_ERROR, uiState))
                                    }
                                }
                            }else{
                                _uiActions.emit(Pair(NavFlow.BOOKING_ERROR, uiState))
                            }
                        }else{
                            if(uiState.startTimeLong == null){
                                uiState.startTimeLong = 0L
                            }
                            if(uiState.endTimeLong == null){
                                uiState.endTimeLong = 0L
                            }
                            if(uiState.reason == null){
                                uiState.reason = ""
                            }
                            _uiState.postValue(uiState)
                        }

                    }
                    else->{
                        _uiActions.emit(Pair(action, uiState))
                    }
                }
            }
        }
    }

    fun checkIfLongIsNullOrZero(long: Long?) : Boolean{
        long?.let {
            if(it > 0L){
                return false
            }
        }
        return true
    }

}