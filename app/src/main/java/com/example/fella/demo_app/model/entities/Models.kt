package com.example.fella.demo_app.model.entities

import com.example.fella.demo_app.adapters.ViewType
import com.example.fella.demo_app.utils.AdapterConstants
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DiscountItem(var id: String,
                        var type: String,
                        var brand: String,
                        var photourl: String,
                        var photourlrollover: String,
                        var url: String,
                        var pricenew: Int,
                        var priceold: Int,
                        var discount: Int,
                        var sizes: ArrayList<String>) : ViewType {
    override fun getViewType(): Int = AdapterConstants.DISCOUNTS
}
