package com.lovoo.lovoooffice.presentation.common.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.lovoo.lovoooffice.R

@BindingAdapter("setGlideImageUrl")
fun setGlideImageUrl(imageView: ImageView, imageUrl : String){
    Glide.with(imageView.context)
        .load(imageUrl)
        .placeholder(R.color.grey_gainsboro)
        .into(imageView)
}