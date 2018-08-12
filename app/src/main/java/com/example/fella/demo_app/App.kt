package com.example.fella.demo_app

import android.app.Application
import com.example.fella.demo_app.di.DaggerDiscountComponent
import com.example.fella.demo_app.di.DiscountComponent

class App : Application() {
    companion object {
        lateinit var discountsComponent: DiscountComponent
    }

    /*TODO: add databinding*/
    override fun onCreate() {
        super.onCreate()
        discountsComponent = DaggerDiscountComponent.builder().build()
    }
}