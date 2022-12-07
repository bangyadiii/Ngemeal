package com.ngemeal.ngemeal.ui.detail

import android.view.View
import com.ngemeal.ngemeal.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PaymentPresenter(private val view : PaymentContract.View) : PaymentContract.Presenter {

    private  val mCompositeDisposable : CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getCheckout(foodId : Int, quantity : Int, viewParams : View) {
        view.showLoading()
        var disposable = HttpClient.getInstance().getApi()?.checkoutProduct(foodId, quantity, "snap-token")!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                view.dismissLoading()

                if (it.meta?.status.equals("success", true)) {
                    it.data.let { it1 -> view.onCheckoutSuccess(it1, viewParams) }
                } else {
                    it.meta?.message?.let { it1 -> view.onCheckoutFailed(it1) }
                }
            }, {
                view.dismissLoading()
                view.onCheckoutFailed( it.message.toString())
            })
        mCompositeDisposable!!.add(disposable)

    }


    override fun subscribe() {
        //
    }

    override fun unSubcribe() {
        this.mCompositeDisposable!!.clear()
    }
}