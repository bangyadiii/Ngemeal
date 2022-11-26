package com.ngemeal.ngemeal.network

import com.ngemeal.ngemeal.model.response.Wrapper
import com.ngemeal.ngemeal.model.response.login.LoginResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Url

interface EndPoint {

    @FormUrlEncoded
    @POST("login")
    fun login(@Field("email") email: String,
              @Field("password") password: String,
              @Field("device_name") deviceName: String
    )
    : Observable<Wrapper<LoginResponse>>

}