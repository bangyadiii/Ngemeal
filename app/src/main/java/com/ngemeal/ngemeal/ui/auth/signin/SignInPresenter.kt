package com.ngemeal.ngemeal.ui.auth.signin

import com.ngemeal.ngemeal.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SignInPresenter(private val view : SignInContract.View) : SignInContract.Presenter {

    private  val mCompositeDisposable : CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun submitLogin(email: String, password: String, deviceName: String) {
        view.showLoading()
        var disposable = HttpClient.getInstance().getApi()?.login(email, password, deviceName)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                view.dismissLoading()

                if (it.meta?.status.equals("success", true)) {
                    view.onLoginSuccess(it.data)
                } else {
                    it.meta?.message?.let { it1 -> view.onLoginFailed(it1, it.errors) }
                }
            }, {

                view.dismissLoading()
                view.onLoginFailed(it.message.toString(), null)
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