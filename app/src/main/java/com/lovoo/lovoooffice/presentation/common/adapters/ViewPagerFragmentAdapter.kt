package com.lovoo.lovoooffice.presentation.common.adapters

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerFragmentAdapter(@NonNull fragmentManager: FragmentManager,
                               @NonNull lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val arrayList: ArrayList<Fragment> = ArrayList()

    fun addFragment(fragment: Fragment) {
        arrayList.add(fragment)
    }

    fun addAllFragments(fragments: List<Fragment>) {
        arrayList.addAll(fragments)
    }

    override fun getItemCount(): Int = arrayList.size

    @NonNull
    override fun createFragment(position: Int): Fragment {
        // return your fragment that corresponds to this 'position'
        return arrayList[position]
    }
}