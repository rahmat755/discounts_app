package com.example.fella.demo_app.presenter

import com.example.fella.demo_app.model.entities.DiscountItem

interface MainContract {
    interface View {
        fun showDiscounts(discounts: ArrayList<DiscountItem>)
        fun showError(error: Throwable)
        fun showProgressBar()
        fun hideProgressBar()
    }
    interface Presenter {
        fun onLoad(page:Int)
        fun onDestroy()
    }
}