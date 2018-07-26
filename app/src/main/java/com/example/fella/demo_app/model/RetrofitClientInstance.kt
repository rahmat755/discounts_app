package com.example.fella.demo_app.model

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit


object RetrofitClientInstance {
    private var retrofit: Retrofit? = null
    private const val BASE_URL = "https://discounts-app123131.herokuapp.com"
    fun getRetrofitInstance(): Retrofit {
        if (retrofit == null) {
            val builder = OkHttpClient.Builder()
            val okHttpClient = builder.build()

            retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()

        }
        return retrofit!!
    }
}