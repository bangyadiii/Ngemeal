package com.ngemeal.ngemeal.ui.home.newtaste

import android.widget.Toast
import com.ngemeal.ngemeal.network.HttpClient
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeNewTastePresenter(private val view : HomeNewTasteContract.View) : HomeNewTasteContract.Presenter {

    private  val mCompositeDisposable : CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getHome(category: String?) {
        view.showLoading()
        var disposable = HttpClient.getInstance().getApi()?.getHome(category)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                view.dismissLoading()

                if (it.meta?.status.equals("success", true)) {
                    it.data.let { it1 -> view.onHomeSuccess(it1) }
                } else {
                    it.meta?.message?.let { it1 -> view.onHomeFailed(it1) }
                }
            }, {
                view.dismissLoading()
                view.onHomeFailed( it.message.toString())
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