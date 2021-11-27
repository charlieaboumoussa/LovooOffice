package com.lovoo.lovoooffice.presentation.officedetails.adapters

import com.lovoo.lovoooffice.common.base.adapters.BaseViewHolder
import com.lovoo.lovoooffice.core.domain.model.OfficeBooking
import com.lovoo.lovoooffice.databinding.ViewHolderOfficeBookingBinding

class OfficeBookingViewHolder(private val _viewBinding : ViewHolderOfficeBookingBinding)
    : BaseViewHolder<OfficeBooking>(_viewBinding.root) {

    override fun onBind(item: OfficeBooking, position: Int) {
        _viewBinding.officeBooking = item
    }

}