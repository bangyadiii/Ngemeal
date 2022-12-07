package com.ngemeal.ngemeal.network

import com.ngemeal.ngemeal.model.response.Wrapper
import com.ngemeal.ngemeal.model.response.checkout.CheckoutResponse
import com.ngemeal.ngemeal.model.response.PaginateResponse
import com.ngemeal.ngemeal.model.response.home.Data
import com.ngemeal.ngemeal.model.response.login.LoginResponse
import com.ngemeal.ngemeal.model.response.login.User
import com.ngemeal.ngemeal.model.response.signup.CheckEmail
import com.ngemeal.ngemeal.model.response.transaction.Transaction
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface EndPoint {

    @FormUrlEncoded
    @POST("login")
    fun login(@Field("email") email: String,
              @Field("password") password: String,
              @Field("device_name") deviceName: String
    )
    : Observable<Wrapper<LoginResponse>>

    @FormUrlEncoded
    @POST("register")
    fun register(@Field("name") name: String,
                 @Field("email") email: String,
                 @Field("password") password: String,
                 @Field("password_confirmation") passwordConfirmation: String,
                 @Field("address") address: String,
                 @Field("city") city: String,
                 @Field("house_number") houseNumber: String,
                 @Field("phone_number") phoneNumber: String,

    )
            : Observable<Wrapper<LoginResponse>>


    @Multipart
    @POST("auth/user/upload-photo")
    fun uploadPhoto(@Part photo : MultipartBody.Part) : Observable<Wrapper<User>>

    @GET("foods")
    fun getHome(@Query("category") category : String? = null,
                @Query("rate") rate : Int? = null,
                @Query("name") name: String? = null,
                @Query("limit") limit : Int? = null,
                ) : Observable<Wrapper<PaginateResponse<Data>>>

    @FormUrlEncoded
    @POST("transactions/checkout")
    fun checkoutProduct(@Field("food_id") foodId : Int,
                        @Field("quantity") quantity : Int,
                        @Query("mode") mode : String?
    ) : Observable<Wrapper<CheckoutResponse>>

    @GET("transactions")
    fun getTransaction(
        @Query("order_id") orderId : String? = null,
        @Query("payment_status") paymentStatus: String? = null,
        @Query("deliver_status") deliverStatus: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("orderBy") orderBy: String? = null,
    ) : Observable<Wrapper<PaginateResponse<Transaction>>>

    @DELETE("logout")
    fun logout() : Observable<Wrapper<String>>

    @GET("check-email")
    fun checkEmail(@Query("email") email : String) : Observable<Wrapper<CheckEmail>>
}