package com.ngemeal.ngemeal.ui.auth.signup

import android.net.Uri
import com.ngemeal.ngemeal.base.BasePresenter
import com.ngemeal.ngemeal.base.BaseView
import com.ngemeal.ngemeal.model.request.RegisterRequest
import com.ngemeal.ngemeal.model.response.login.LoginResponse

interface SignUpContract {
    interface  View:BaseView{
        fun onRegisterSuccess(registerResponse : LoginResponse, view: android.view.View)
        fun onRegisterPhotoSuccess(view : android.view.View)
        fun onRegisterFailed(message : String)
    }

    interface Presenter : SignUpContract, BasePresenter{
        fun submitRegister(request : RegisterRequest, view : android.view.View)
        fun submitPhotoAvatar(filePathUri : Uri, view : android.view.View)
    }
}