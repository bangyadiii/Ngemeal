package com.ngemeal.ngemeal.ui.order.pastorders

import com.ngemeal.ngemeal.base.BasePresenter
import com.ngemeal.ngemeal.base.BaseView
import com.ngemeal.ngemeal.model.response.PaginateResponse
import com.ngemeal.ngemeal.model.response.transaction.Transaction
import com.ngemeal.ngemeal.ui.order.OrderContract

interface PastOrderContract {
    interface  View:BaseView{
        fun onTransactionSuccess(transactionResponse: PaginateResponse<Transaction>)
        fun onTransactionFailed(message : String)
    }

    interface Presenter : OrderContract, BasePresenter{
        fun getTransaction(paymentStatus : String? = null, deliverStatus : String? = null ,limit : Int? = null, orderBy : String? =null)
    }
}