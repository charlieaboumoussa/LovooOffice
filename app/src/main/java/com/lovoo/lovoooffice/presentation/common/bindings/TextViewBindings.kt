package com.lovoo.lovoooffice.presentation.common.bindings

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("setDateAsText")
fun setDateAsText(textView: TextView, dateLong : Long?){
    dateLong?.let {
        val date = Date(dateLong)
        val dateFormat = SimpleDateFormat("MMM dd yyyy", Locale.getDefault())
        textView.text = dateFormat.format(date)
    }
}