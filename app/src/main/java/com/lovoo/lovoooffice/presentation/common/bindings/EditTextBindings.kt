package com.lovoo.lovoooffice.presentation.common.bindings

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.lovoo.lovoooffice.R
import java.text.SimpleDateFormat

@BindingAdapter("populateAutoCompleteTextView", "shouldRepopulate", requireAll = false)
fun populateAutoCompleteTextView(autoCompleteTextView: AutoCompleteTextView, items : List<String>?, shouldRepopulate : Boolean?){
    items?.let {
        if(shouldRepopulate != null){
            if(shouldRepopulate){
                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(autoCompleteTextView.context,
                    R.layout.layout_autocomplete_textview_item,
                    items.toMutableList()
                )
                autoCompleteTextView.setAdapter(adapter)
            }
        }else{
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(autoCompleteTextView.context,
                R.layout.layout_autocomplete_textview_item,
                items.toMutableList()
            )
            autoCompleteTextView.setAdapter(adapter)
        }
    }
}

@BindingAdapter("parentLayout", "setTextOrError", requireAll = false)
fun setTextOrError(editText: TextInputEditText, textInputLayout: TextInputLayout?, text : String?) {
    text?.let {
        if(text.isNotEmpty()){
            textInputLayout?.error = null
            textInputLayout?.isErrorEnabled = false
            editText.setText(text)
        }else{
            textInputLayout?.isErrorEnabled = true
            textInputLayout?.error = textInputLayout?.context?.getString(R.string.error_field_required)
        }
    }
}

@BindingAdapter("parentLayout", "setDateTextOrError", "formatter", requireAll = false)
fun setDateTextOrError(autoCompleteTextView: AutoCompleteTextView, textInputLayout: TextInputLayout?, dateOrTimeLong : Long?, formatter : SimpleDateFormat?){
    dateOrTimeLong?.let {
        if(it > 0L){
            textInputLayout?.error = null
            textInputLayout?.isErrorEnabled = false
            autoCompleteTextView.setText(formatter?.format(dateOrTimeLong))
        }else{
            textInputLayout?.isErrorEnabled = true
            textInputLayout?.error = textInputLayout?.context?.getString(R.string.error_field_required)
        }
    }
}