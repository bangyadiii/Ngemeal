package com.ngemeal.ngemeal.ui.profile

import com.ngemeal.ngemeal.base.BasePresenter
import com.ngemeal.ngemeal.base.BaseView
import com.ngemeal.ngemeal.model.response.PaginateResponse
import com.ngemeal.ngemeal.model.response.transaction.Transaction

interface ProfileManagementContract {
    interface  View:BaseView{
        fun onLogoutSuccess()
        fun onLogoutFailed(message : String)
    }

    interface Presenter : ProfileManagementContract, BasePresenter{
        fun logout()
    }
}