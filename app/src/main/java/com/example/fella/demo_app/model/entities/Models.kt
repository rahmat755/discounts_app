package com.example.fella.demo_app.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class DiscountItem {
        @SerializedName("id")
        @Expose
        var id: String? = null
        @SerializedName("type")
        @Expose
        var type: String? = null
        @SerializedName("brand")
        @Expose
        var brand: String? = null
        @SerializedName("photourl")
        @Expose
        var photourl: String? = null
        @SerializedName("photourlrollover")
        @Expose
        var photourlrollover: String? = null
        @SerializedName("url")
        @Expose
        var url: String? = null
        @SerializedName("pricenew")
        @Expose
        var pricenew: Int? = null
        @SerializedName("priceold")
        @Expose
        var priceold: Int? = null
        @SerializedName("discount")
        @Expose
        var discount: Int? = null
}