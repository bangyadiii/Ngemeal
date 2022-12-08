package com.ngemeal.ngemeal.ui.order.detailorders

import com.ngemeal.ngemeal.base.BasePresenter
import com.ngemeal.ngemeal.base.BaseView

interface OrdersDetailContract {
    interface View : BaseView{
        fun onUpdateTransactionSuccess(message: String)
        fun onUpdateTransactionFailed(message: String)
    }

    interface Presenter : OrdersDetailContract, BasePresenter{
        fun getUpdateTransaction(id:String, status:String)
    }

}