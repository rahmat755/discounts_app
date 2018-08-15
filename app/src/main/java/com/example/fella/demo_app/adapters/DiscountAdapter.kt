package com.example.fella.demo_app.adapters

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.example.fella.demo_app.model.entities.DiscountItem
import com.example.fella.demo_app.utils.AdapterConstants

class DiscountAdapter(listener: DiscountDelegateAdapter.OnViewSelectedListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var discountItems: ArrayList<ViewType> = arrayListOf()
    private var discountNotHotItems: ArrayList<ViewType> = arrayListOf()
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

    fun addItems(discounts: ArrayList<DiscountItem>, show: Boolean = true) {
        val initPosition = discountItems.size - 1
        discountItems.removeAt(initPosition)
        notifyItemRemoved(initPosition)
//        for (discount in discounts) {
//            if (discountItems.contains(discount)) {
//            } else {
                discountItems.addAll(discounts)
//            }
//        }
        if (show)
            discountItems.add(loadingItem)
        notifyItemRangeInserted(initPosition, discountItems.size + 1)
    }

    fun addAll(discounts: ArrayList<DiscountItem>, show: Boolean = true) {
        discountItems.clear()
        discountItems.addAll(discounts)
        if (show)
            discountItems.add(loadingItem)
        notifyDataSetChanged()
    }

    fun addAndFilterItems(discounts: ArrayList<DiscountItem>, show: Boolean = true) {
        val initPosition = discountItems.size - 1
        discountItems.removeAt(initPosition)
        notifyItemRemoved(initPosition)
        val hotDiscounts = ArrayList(discounts.filter {
            it.discount >= 40
        })
        discountItems.addAll(hotDiscounts)
        if (show)
            discountItems.add(loadingItem)
        notifyItemRangeChanged(initPosition, discountItems.size + 1)
    }

    fun filter(isHot: Boolean) {
        if (isHot) {
            discountNotHotItems = ArrayList(discountItems.filterIsInstance(DiscountItem::class.java)
                    .filter {
                        Log.d("items", it.id)
                        it.discount <= 40
                    })
            discountItems = ArrayList(discountItems - discountNotHotItems)
            notifyDataSetChanged()
        } else {
            discountItems.clear()
            notifyDataSetChanged()
        }
    }

    fun removeAllItems() {
        discountItems.clear()
        discountItems.add(loadingItem)
        notifyDataSetChanged()
    }
    private fun getLastPosition() = if (discountItems.lastIndex == -1) 0 else discountItems.lastIndex
}