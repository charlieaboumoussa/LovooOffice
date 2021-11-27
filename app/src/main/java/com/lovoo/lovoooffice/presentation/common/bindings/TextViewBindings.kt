package com.lovoo.lovoooffice.presentation.common.bindings

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("setDateAsText", "dateFormat", requireAll = false)
fun setDateAsText(textView: TextView, dateLong : Long, format : String = "MMM dd, yyyy hh:mm a"){
    val date = Date(dateLong)
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    textView.text = dateFormat.format(date)
}