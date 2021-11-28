package com.lovoo.lovoooffice.presentation.officedetails.adapters

import com.lovoo.lovoooffice.common.base.adapters.BaseViewHolder
import com.lovoo.lovoooffice.core.domain.model.OfficeBooking
import com.lovoo.lovoooffice.databinding.ViewHolderOfficeBookingBinding
import java.text.SimpleDateFormat
import java.util.*

class OfficeBookingViewHolder(private val _viewBinding : ViewHolderOfficeBookingBinding)
    : BaseViewHolder<OfficeBooking>(_viewBinding.root) {

    override fun onBind(item: OfficeBooking, position: Int) {
        _viewBinding.officeBooking = item
        val timeFormatter = SimpleDateFormat("hh:mm aa", Locale.getDefault())
        val formattedTime = String.format("%s - %s", timeFormatter.format(item.startTime), timeFormatter.format(item.endTime))
        _viewBinding.textViewTime.text = formattedTime
    }

}