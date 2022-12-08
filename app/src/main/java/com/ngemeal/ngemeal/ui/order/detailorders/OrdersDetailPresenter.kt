package com.ngemeal.ngemeal.ui.order.detailorders

import com.ngemeal.ngemeal.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class OrdersDetailPresenter(private val view : OrdersDetailContract.View) : OrdersDetailContract.Presenter {
    private val mCompositeDisposable : CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }



    override fun getUpdateTransaction(id: String, status: String) {
        val disposable = HttpClient.getInstance().getApi()!!.getTransaction(id, status)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success")) {
                        view.onUpdateTransactionSuccess(it.meta?.message.toString())
                    } else {
                        view.onUpdateTransactionFailed(it.meta?.message.toString())
                    }

                },
                {
                    view.dismissLoading()
                    view.onUpdateTransactionFailed(it.message.toString())
                })
        mCompositeDisposable!!.add(disposable)

    }

    override fun subscribe() {
        //
    }

    override fun unSubcribe() {
        mCompositeDisposable!!.clear()
    }
}