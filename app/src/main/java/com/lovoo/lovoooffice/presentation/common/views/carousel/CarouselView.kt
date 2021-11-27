package com.lovoo.lovoooffice.presentation.common.views.carousel

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.fragment.app.findFragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lovoo.lovoooffice.common.base.ui.BaseFragment
import com.lovoo.lovoooffice.databinding.LayoutCarouselViewBinding
import com.lovoo.lovoooffice.presentation.common.adapters.ViewPagerFragmentAdapter
import com.lovoo.lovoooffice.presentation.common.views.carousel.model.ImageCarouselItem

class CarouselView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var binding : LayoutCarouselViewBinding

    init {
        binding = LayoutCarouselViewBinding.inflate(LayoutInflater.from(context), this, true)
    }
}

@BindingAdapter("carouselItems")
fun setCarouselItems(carousel : CarouselView, items : List<String>?){
    setCarouselViewPagerItems(carousel.binding.viewPager2, items)
}

@BindingAdapter("carouselViewPagerItems", "attachTabLayout", requireAll = false)
fun setCarouselViewPagerItems(viewPager : ViewPager2, items : List<String>?, tabLayout: TabLayout? = null){
    items?.let {
        val viewPagerAdapter = getOrCreateAdapter(viewPager)
        it.forEachIndexed { index, item->
            val fragment = CarouselItemFragment(ImageCarouselItem(item))
            viewPagerAdapter.addFragment(fragment)
            var isSelected = false
            if(index == 0){
                isSelected = true
            }
            tabLayout?.addTab(tabLayout.newTab(), isSelected)
        }
        tabLayout?.let {
            setupTabsWithViewPager(tabLayout, viewPager)
        }
//        viewPager.setCurrentItem(0, false)
    }
}

fun getOrCreateAdapter(viewPager: ViewPager2): ViewPagerFragmentAdapter {
    return if (viewPager.adapter != null) {
        viewPager.adapter as ViewPagerFragmentAdapter
    } else {
        val fragment = viewPager.findFragment<BaseFragment>()
        val adapter = ViewPagerFragmentAdapter(fragment.childFragmentManager, fragment.lifecycle)
        viewPager.adapter = adapter
        adapter
    }
}

fun setupTabsWithViewPager(tabLayout: TabLayout, viewPager : ViewPager2){
    TabLayoutMediator(tabLayout, viewPager, object : TabLayoutMediator.TabConfigurationStrategy {
        override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {

        }
    }).attach()
}

