package com.ngemeal.ngemeal.ui.home

import com.ngemeal.ngemeal.base.BasePresenter
import com.ngemeal.ngemeal.base.BaseView
import com.ngemeal.ngemeal.model.response.home.Data
import com.ngemeal.ngemeal.model.response.PaginateResponse

interface HomeContract {
    interface  View:BaseView{
        fun onHomeSuccess(homeResponse: PaginateResponse<Data>)
        fun onHomeFailed(message : String)
    }

    interface Presenter : HomeContract, BasePresenter{
        fun getHome()
    }
}