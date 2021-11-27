package com.lovoo.lovoooffice.presentation.common.views.carousel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lovoo.lovoooffice.common.base.ui.BaseFragment
import com.lovoo.lovoooffice.databinding.FragmentCarouselItemBinding
import com.lovoo.lovoooffice.presentation.common.views.carousel.model.BaseCarouselItem
import com.lovoo.lovoooffice.presentation.common.views.carousel.model.ImageCarouselItem

class CarouselItemFragment(private val carouselItem : BaseCarouselItem) : BaseFragment(){

    private lateinit var _binding: FragmentCarouselItemBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarouselItemBinding.inflate(inflater, container, false)
        _binding.setLifecycleOwner(this)
        when(carouselItem){
            is ImageCarouselItem->{
                _binding.imageUrl = carouselItem.imageUrl
            }
            else->{

            }
        }
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}