package com.ngemeal.ngemeal.ui.detail

import com.ngemeal.ngemeal.base.BasePresenter
import com.ngemeal.ngemeal.base.BaseView
import com.ngemeal.ngemeal.model.response.checkout.CheckoutResponse

interface PaymentContract {
    interface  View :BaseView{
        fun onCheckoutSuccess(checkoutResponse: CheckoutResponse, view : android.view.View)
        fun onCheckoutFailed(message : String)
    }

    interface Presenter : PaymentContract, BasePresenter{
        fun getCheckout(foodId : Int, quantity : Int, view: android.view.View)
    }
}