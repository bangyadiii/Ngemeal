package com.ngemeal.ngemeal.ui.auth.signup

import android.net.Uri
import android.view.View
import com.ngemeal.ngemeal.model.request.RegisterRequest
import com.ngemeal.ngemeal.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class SignUpPresenter(private val view : SignUpContract.View) : SignUpContract.Presenter {

    private  val mCompositeDisposable : CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun submitRegister(request: RegisterRequest, viewParms: View) {
        view.showLoading()
        var disposable = HttpClient.getInstance().getApi()?.register(
           request.name,
           request.email,
           request.password,
           request.passwordConf,
           request.address,
           request.city,
           request.houseNumber,
           request.phoneNumber
        )!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                view.dismissLoading()

                if (it.meta?.status.equals("success", true)) {
                    view.onRegisterSuccess(it.data, viewParms)
                } else {
                    it.meta?.message?.let { it1 -> view.onRegisterFailed(it1) }
                }
            }, {
                view.dismissLoading()
                view.onRegisterFailed(it.message.toString())
            })
        mCompositeDisposable!!.add(disposable)
    }

    override fun submitPhotoAvatar(filePathUri: Uri, viewParms: View) {
        view.showLoading()
        // buat image dari URI
        var fileImageProfile = File(filePathUri.path)

        // buat multipart request body
        var profileImageRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), fileImageProfile)
        var photoAvatarParms = MultipartBody.Part.createFormData("photo", fileImageProfile.name, profileImageRequestBody)

        var disposable = HttpClient.getInstance().getApi()?.uploadPhoto(photoAvatarParms)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                view.dismissLoading()
                if (it.meta?.status.equals("success", true)) {
                    it.data.let { it1 -> view.onRegisterPhotoSuccess(viewParms) }
                } else {
                    view.onRegisterFailed(it.meta?.message.toString())
                }
            }, {
                view.dismissLoading()
                view.onRegisterFailed(it.message.toString())
            })
        mCompositeDisposable!!.add(disposable)
    }

    override fun subscribe() {
        TODO("Not yet implemented")
    }

    override fun unSubcribe() {
        this.mCompositeDisposable!!.clear()
    }
}

