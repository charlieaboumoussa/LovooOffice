package com.lovoo.lovoooffice.presentation.landing.fragments.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lovoo.lovoooffice.common.base.adapters.BaseViewHolder
import com.lovoo.lovoooffice.core.domain.model.LovooFact
import com.lovoo.lovoooffice.core.domain.model.Office
import com.lovoo.lovoooffice.core.domain.model.OfficeBooking
import com.lovoo.lovoooffice.databinding.ViewHolderOfficeBinding
import com.lovoo.lovoooffice.databinding.ViewHolderOfficeBookingBinding

class OfficesAdapter : RecyclerView.Adapter<BaseViewHolder<Office>>() {

    private val _items : ArrayList<Office> = arrayListOf()
    private var _officeAdapterInterface: OfficeAdapterInterface? = null

    interface OfficeAdapterInterface {
        fun onOfficeClicked(office: Office){}
        fun onLovooFactClicked(lovooFact: LovooFact){}
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Office> {
        return OfficeViewHolder(
            ViewHolderOfficeBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Office>, position: Int) {
        val item = _items.get(holder.adapterPosition)
        if(holder is OfficeViewHolder){
            holder.onBind(item, holder.adapterPosition)
            holder.itemView.setOnClickListener {
                _officeAdapterInterface?.onOfficeClicked(item)
            }
            item.lovooFact?.let { lovooFact->
                holder.setOnEndIconClicked {
                    _officeAdapterInterface?.onLovooFactClicked(lovooFact)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        var count = 0
        count = _items.size
        return count
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items : ArrayList<Office>){
        _items.clear()
        _items.addAll(items)
        notifyDataSetChanged()
    }

    fun setOfficeAdapterInterface(officeAdapterInterface:OfficeAdapterInterface){
        _officeAdapterInterface = officeAdapterInterface
    }
}

@BindingAdapter("officeItems", "onItemClicked", "onEndIconClicked", requireAll = false)
fun setOfficeItems(recyclerView: RecyclerView,
                   items: List<Office>?,
                   onItemClicked : ((office : Office)->Unit)?,
                   onEndIconClicked : ((lovooFact : LovooFact)->Unit)?) {
    items?.let {
        val adapter = getOrCreateAdapter(recyclerView, onItemClicked, onEndIconClicked)
        adapter.setItems(ArrayList(it))
    }
}

fun getOrCreateAdapter(recyclerView: RecyclerView,
                       onItemClicked : ((office : Office)->Unit)?,
                       onEndIconClicked : ((lovooFact : LovooFact)->Unit)?): OfficesAdapter {
    return if (recyclerView.adapter != null && recyclerView.adapter is OfficesAdapter) {
        recyclerView.adapter as OfficesAdapter
    } else {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)
        val adapter = OfficesAdapter()
        adapter.setOfficeAdapterInterface(object : OfficesAdapter.OfficeAdapterInterface{
            override fun onOfficeClicked(office: Office) {
                onItemClicked?.invoke(office)
            }

            override fun onLovooFactClicked(lovooFact: LovooFact) {
                onEndIconClicked?.invoke(lovooFact)
            }
        })
        recyclerView.adapter = adapter
        adapter
    }
}