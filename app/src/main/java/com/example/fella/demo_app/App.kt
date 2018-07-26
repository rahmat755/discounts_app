package com.example.fella.demo_app

import android.app.Application
import android.os.Parcelable
import com.example.fella.demo_app.model.entities.DiscountItem
import io.reactivex.disposables.CompositeDisposable

class App : Application() {
    companion object {
        val compositeDisposable = CompositeDisposable()
        var page: Int=1
        var discounts_man : ArrayList<DiscountItem> = arrayListOf()
        var discounts_woman : ArrayList<DiscountItem> = arrayListOf()
        var discounts_child : ArrayList<DiscountItem> = arrayListOf()
        var state: Parcelable? = null
    }
}