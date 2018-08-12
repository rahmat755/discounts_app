package com.example.fella.demo_app.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.fella.demo_app.R
import com.example.fella.demo_app.model.entities.DiscountItem
import com.example.fella.demo_app.utils.inflate
import com.example.fella.demo_app.utils.loadImg
import java.util.ArrayList

class DiscountAdapter(private val listener: OnViewSelectedListener): RecyclerView.Adapter<DiscountAdapter.DiscountViewHolder>() {
    interface OnViewSelectedListener{
        fun onItemClicked(itemURL: String)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscountViewHolder {
        val itemView = parent.inflate(R.layout.dicount_item)
        return DiscountViewHolder(itemView)
    }

    private var previousPosition: Int? = null
    private var discountItems: ArrayList<DiscountItem> = arrayListOf()
    override fun onBindViewHolder(holder: DiscountViewHolder, position: Int) {
        holder.itemBrand?.text = discountItems[position].brand
        holder.itemType?.text = discountItems[position].type
        holder.oldPrice?.text = discountItems[position].priceold.toString()
        holder.newPrice?.text = discountItems[position].pricenew.toString()
        holder.itemPreview?.loadImg(discountItems[position].photourl!!)
        holder.itemView.setOnClickListener { listener.onItemClicked(discountItems[position].url!!) }
    }

    fun addItems(items: ArrayList<DiscountItem>) {
        previousPosition = discountItems.size
        discountItems.addAll(items)
        notifyDataSetChanged()
    }

    fun removeAllItems() {
        previousPosition = itemCount
        discountItems.clear()
        notifyItemRangeRemoved(0, previousPosition!!)
    }

    override fun getItemCount(): Int = discountItems.size


    inner class DiscountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemType: TextView? = null
        var itemBrand: TextView? = null
        var oldPrice: TextView? = null
        var newPrice: TextView? = null
        var itemPreview: ImageView? = null

        init {
            itemBrand = itemView.findViewById(R.id.item_brand)
            itemType = itemView.findViewById(R.id.item_type)
            oldPrice = itemView.findViewById(R.id.previous_price)
            newPrice = itemView.findViewById(R.id.price_now)
            itemPreview = itemView.findViewById(R.id.preview)

        }
    }
}