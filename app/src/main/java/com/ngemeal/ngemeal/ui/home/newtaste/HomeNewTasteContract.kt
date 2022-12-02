package com.ngemeal.ngemeal.ui.home.newtaste

import android.net.Uri
import com.ngemeal.ngemeal.base.BasePresenter
import com.ngemeal.ngemeal.base.BaseView
import com.ngemeal.ngemeal.model.request.RegisterRequest
import com.ngemeal.ngemeal.model.response.home.HomeResponse
import com.ngemeal.ngemeal.model.response.login.LoginResponse

interface HomeNewTasteContract {
    interface  View:BaseView{
        fun onHomeSuccess(homeResponse: HomeResponse)
        fun onHomeFailed(message : String)
    }

    interface Presenter : HomeNewTasteContract, BasePresenter{
        fun getHome(category: String?)
    }
}