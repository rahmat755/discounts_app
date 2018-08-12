package com.example.fella.demo_app.adapters

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.fella.demo_app.model.entities.DiscountItem
import com.example.fella.demo_app.utils.AdapterConstants

class DiscountAdapter(listener: DiscountDelegateAdapter.OnViewSelectedListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var discountItems: ArrayList<ViewType>  = arrayListOf()
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()

    private val loadingItem = object : ViewType {
        override fun getViewType() = AdapterConstants.LOADING
    }

    init {
        delegateAdapters.put(AdapterConstants.LOADING, LoadingDelegateAdapter())
        delegateAdapters.put(AdapterConstants.DISCOUNTS, DiscountDelegateAdapter(listener))
        discountItems.add(loadingItem)
    }

    override fun getItemCount(): Int = discountItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            delegateAdapters.get(viewType).onCreateViewHolder(parent)


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, discountItems[position])
    }

    override fun getItemViewType(position: Int) = discountItems[position].getViewType()

    fun addItems(city: ArrayList<DiscountItem>, show: Boolean = true) {
        val initPosition = discountItems.size - 1
        discountItems.removeAt(initPosition)
        notifyItemRemoved(initPosition)
        discountItems.addAll(city)
        if (show)
            discountItems.add(loadingItem)
        notifyItemRangeChanged(initPosition, discountItems.size + 1 )
    }

    fun removeAllItems() {
        discountItems.clear()
        discountItems.add(loadingItem)
        notifyDataSetChanged()
    }
}