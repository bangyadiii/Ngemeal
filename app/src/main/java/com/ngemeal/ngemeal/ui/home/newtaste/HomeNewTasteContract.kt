package com.ngemeal.ngemeal.ui.home.newtaste

import com.ngemeal.ngemeal.base.BasePresenter
import com.ngemeal.ngemeal.base.BaseView
import com.ngemeal.ngemeal.model.response.home.Data
import com.ngemeal.ngemeal.model.response.PaginateResponse

interface HomeNewTasteContract {
    interface  View:BaseView{
        fun onHomeSuccess(homeResponse: PaginateResponse<Data>)
        fun onHomeFailed(message : String)
    }

    interface Presenter : HomeNewTasteContract, BasePresenter{
        fun getHome(category: String?)
    }
}