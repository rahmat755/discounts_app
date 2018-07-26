package com.example.fella.demo_app.presenter

import android.util.Log
import com.example.fella.demo_app.App
import com.example.fella.demo_app.model.DiscountApi
import com.example.fella.demo_app.model.RetrofitClientInstance
import com.example.fella.demo_app.model.entities.DiscountItem
//import com.example.fella.demo_app.model.impl.MainRepository
import com.example.fella.demo_app.presenter.MainContract.View
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
private const val TAG = "Woman Presenter"

class ManDiscountPresenter(var mView: MainContract.View) : MainContract.Presenter {

    var discounts: ArrayList<DiscountItem> = arrayListOf()

    override fun onDestroy() {
        App.discounts_man.addAll(this.discounts)
        App.compositeDisposable.clear()
    }

    override fun onLoad(page: Int) {
        mView.showProgressBar()
        App.compositeDisposable.add(getObservable(page).subscribeWith<DisposableObserver<ArrayList<DiscountItem>>>(getObserver()))
    }


    private fun getObservable(page: Int): Observable<ArrayList<DiscountItem>> {
        return RetrofitClientInstance.getRetrofitInstance().create(DiscountApi::class.java)
                .getManDiscounts(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getObserver(): DisposableObserver<ArrayList<DiscountItem>> {
        return object : DisposableObserver<ArrayList<DiscountItem>>() {
            override fun onNext(@NonNull discountResponse: ArrayList<DiscountItem>) {
                Log.d(TAG, "Man $discountResponse")
                mView.hideProgressBar()
                App.discounts_man.addAll(discountResponse)
                mView.showDiscounts(discountResponse)
                App.page++
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
                mView.showError(e)
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
            }
        }
    }


}