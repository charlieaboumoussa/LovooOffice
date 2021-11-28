package com.lovoo.lovoooffice.presentation.officedetails.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lovoo.lovoooffice.common.base.adapters.BaseViewHolder
import com.lovoo.lovoooffice.core.domain.model.OfficeBooking
import com.lovoo.lovoooffice.databinding.ViewHolderOfficeBookingBinding

class OfficeBookingsAdapter : RecyclerView.Adapter<BaseViewHolder<OfficeBooking>>() {

    private val _items : ArrayList<OfficeBooking> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<OfficeBooking> {
        return OfficeBookingViewHolder(ViewHolderOfficeBookingBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<OfficeBooking>, position: Int) {
        val item = _items.get(holder.adapterPosition)
        if(holder is OfficeBookingViewHolder){
            holder.onBind(item, holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        var count = 0
        count = _items.size
        return count
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items : ArrayList<OfficeBooking>){
        _items.clear()
        _items.addAll(items)
        notifyDataSetChanged()
    }
}

@BindingAdapter("officeBookingItems")
fun setOfficeBookingsItems(recyclerView: RecyclerView, items: List<OfficeBooking>?) {
    items?.let {
        val adapter = getOrCreateAdapter(recyclerView)
        adapter.setItems(ArrayList(it))
    }
}

fun getOrCreateAdapter(recyclerView: RecyclerView): OfficeBookingsAdapter {
    return if (recyclerView.adapter != null && recyclerView.adapter is OfficeBookingsAdapter) {
        recyclerView.adapter as OfficeBookingsAdapter
    } else {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context,LinearLayoutManager.VERTICAL, false)
        val adapter = OfficeBookingsAdapter()
        recyclerView.adapter = adapter
        adapter
    }
}