package com.lovoo.lovoooffice.common.base.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import java.util.concurrent.atomic.AtomicBoolean

open class BaseFragment : Fragment() {
    //TODO[11/20/2021] add any customization needed at BaseFragment level

    protected var hasInitializedRootView = AtomicBoolean(false)
    private var _rootView: View? = null

    fun getPersistentView(layout: Int, inflater: LayoutInflater, container: ViewGroup?): View {
        if (_rootView == null) {
            // Inflate the layout for this fragment
            _rootView = inflater.inflate(layout, container,false)
        } else {
            // Do not inflate the layout again.
            // The returned View of onCreateView will be added into the fragment.
            // However it is not allowed to be added twice even if the parent is same.
            // So we must remove rootView from the existing parent view group
            // (it will be added back).
            (_rootView?.getParent() as? ViewGroup)?.removeView(_rootView)
        }

        return _rootView!!
    }

    fun getBindingPersistentView(view : View): View {
        if (_rootView == null) {
            _rootView = view
        } else {
            // Do not inflate the layout again.
            // The returned View of onCreateView will be added into the fragment.
            // However it is not allowed to be added twice even if the parent is same.
            // So we must remove rootView from the existing parent view group
            // (it will be added back).
            (_rootView?.getParent() as? ViewGroup)?.removeView(_rootView)
        }

        return _rootView!!
    }
}