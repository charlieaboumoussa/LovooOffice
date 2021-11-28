package com.lovoo.lovoooffice.presentation.landing.fragments.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.lovoo.lovoooffice.common.base.adapters.BaseViewHolder
import com.lovoo.lovoooffice.core.domain.model.Office
import com.lovoo.lovoooffice.databinding.ViewHolderOfficeBinding

class OfficeViewHolder(val viewBinding : ViewHolderOfficeBinding)
    : BaseViewHolder<Office>(viewBinding.root) {

    override fun onBind(item: Office, position: Int) {
        viewBinding.office = item
    }

    fun setOnEndIconClicked(listener : ()->Unit){
        viewBinding.layoutLovooFact.setOnClickListener {
            listener()
        }
    }

}

@BindingAdapter("setTextWithFallback")
fun setTextWithFallback(textView: TextView, text : String?){
    text?.let {
        if(it.isEmpty().not()){
            textView.text = it
        }else{
            textView.text = "-"
        }
    }
}