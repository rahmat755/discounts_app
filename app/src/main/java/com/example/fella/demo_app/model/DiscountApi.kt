package com.example.fella.demo_app.model

import com.example.fella.demo_app.model.entities.DiscountItem
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscountApi {
    @GET("/discounts/man")
    fun getManDiscounts(@Query("page") page : Int): Flowable<ArrayList<DiscountItem>>
    @GET("/discounts/woman")
    fun getWomanDiscounts(@Query("page") page : Int): Flowable<ArrayList<DiscountItem>>
    @GET("/discounts/child")
    fun getChildDiscounts(@Query("page") page : Int): Flowable<ArrayList<DiscountItem>>
}