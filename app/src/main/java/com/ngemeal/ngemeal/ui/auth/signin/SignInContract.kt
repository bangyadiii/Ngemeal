package com.ngemeal.ngemeal.ui.auth.signin

import com.ngemeal.ngemeal.base.BasePresenter
import com.ngemeal.ngemeal.base.BaseView
import com.ngemeal.ngemeal.model.response.login.LoginResponse

interface SignInContract {
    interface  View:BaseView{
        fun onLoginSuccess(loginResponse : LoginResponse)
        fun onLoginFailed(message : String)
    }

    interface Presenter : SignInContract, BasePresenter{
        fun submitLogin(email : String, password: String, deviceName : String)
    }
}