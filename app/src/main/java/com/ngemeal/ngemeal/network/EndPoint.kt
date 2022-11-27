package com.ngemeal.ngemeal.network

import com.ngemeal.ngemeal.model.response.Wrapper
import com.ngemeal.ngemeal.model.response.login.LoginResponse
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Url

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
    fun uploadPhoto(@Part photo : MultipartBody.Part) : Observable<Wrapper<Any>>

}