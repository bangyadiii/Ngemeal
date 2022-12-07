package com.ngemeal.ngemeal.ui.order.inprogress

import com.ngemeal.ngemeal.network.HttpClient
import com.ngemeal.ngemeal.ui.order.OrderContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class InProgressPresenter(private val view : OrderContract.View) : OrderContract.Presenter {

    private  val mCompositeDisposable : CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }


    override fun getTransaction(paymentStatus: String?, deliverStatus: String?, limit: Int?, orderBy : String?) {
        view.showLoading()
        var disposable = HttpClient.getInstance().getApi()?.getTransaction(paymentStatus = paymentStatus, deliverStatus = deliverStatus, limit = limit, orderBy = orderBy)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                view.dismissLoading()

                if (it.meta?.status.equals("success", true)) {
                    view.onTransactionSuccess(it.data)
                } else {
                    it.meta?.message?.let { it1 -> view.onTransactionFailed(it1) }
                }
            }, {
                view.dismissLoading()
                view.onTransactionFailed( it.message.toString())
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