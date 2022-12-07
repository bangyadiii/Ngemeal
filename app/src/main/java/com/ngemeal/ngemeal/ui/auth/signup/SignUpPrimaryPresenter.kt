package com.ngemeal.ngemeal.ui.auth.signup

import android.net.Uri
import android.view.View
import com.ngemeal.ngemeal.model.request.RegisterRequest
import com.ngemeal.ngemeal.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class SignUpPrimaryPresenter(private val view : CheckEmailContract.View) : CheckEmailContract.Presenter {

    private  val mCompositeDisposable : CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }


    override fun checkEmailAvailable(email: String, viewParms: View) {
        view.showLoading()

        var disposable = HttpClient.getInstance().getApi()?.checkEmail(email)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                view.dismissLoading()
                if (it.meta?.status.equals("success", true)) {
                    it.data.let { it1 -> view.onCheckEmailSuccess(it1, viewParms) }
                } else {
                    view.onCheckEmailFailed(it.meta?.message.toString(), it.errors!!)
                }
            }, {
                view.dismissLoading()
                view.onCheckEmailFailed(it.message.toString(), null)
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

