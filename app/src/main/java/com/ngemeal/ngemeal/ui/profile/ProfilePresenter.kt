package com.ngemeal.ngemeal.ui.profile

import com.ngemeal.ngemeal.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProfilePresenter(private val view : ProfileManagementContract.View) : ProfileManagementContract.Presenter {

    private  val mCompositeDisposable : CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun logout() {
        view.showLoading()
        var disposable = HttpClient.getInstance().getApi()?.logout()!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                view.dismissLoading()

                if (it.meta?.status.equals("success", true)) {
                    view.onLogoutSuccess()
                } else {
                    view.onLogoutFailed(it.meta?.message!!)
                }
            }, {
                view.dismissLoading()
                view.onLogoutFailed( it.message.toString())
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