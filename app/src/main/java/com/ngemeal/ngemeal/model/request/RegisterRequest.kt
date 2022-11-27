package com.ngemeal.ngemeal.model.request

import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class RegisterRequest (
    @Expose
    @SerializedName("name")
    var name : String,

    @Expose
    @SerializedName("email")
    var email : String,

    @Expose
    @SerializedName("password")
    var password : String,

    @Expose
    @SerializedName("password_confirmation")
    var passwordConf : String,

    @Expose
    @SerializedName("address")
    var address : String,

    @Expose
    @SerializedName("photo_avatar")
    var filePath : Uri?,

    @Expose
    @SerializedName("city")
    var city : String,

    @Expose
    @SerializedName("house_number")
    var houseNumber : String,

    @Expose
    @SerializedName("phone_number")
    var phoneNumber : String
): Parcelable