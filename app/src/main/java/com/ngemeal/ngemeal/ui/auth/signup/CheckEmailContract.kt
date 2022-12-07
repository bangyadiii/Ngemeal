package com.ngemeal.ngemeal.ui.auth.signup

import android.net.Uri
import com.ngemeal.ngemeal.base.BasePresenter
import com.ngemeal.ngemeal.base.BaseView
import com.ngemeal.ngemeal.model.request.RegisterRequest
import com.ngemeal.ngemeal.model.response.login.LoginResponse
import com.ngemeal.ngemeal.model.response.signup.CheckEmail

interface CheckEmailContract {
    interface  View:BaseView{
        fun onCheckEmailSuccess(response : CheckEmail, view: android.view.View)
        fun onCheckEmailFailed(message : String, errors : Map<String, List<String>>?)
    }

    interface Presenter : CheckEmailContract, BasePresenter{
        fun checkEmailAvailable(email : String, view: android.view.View)
    }
}