package com.ngemeal.ngemeal.model.response.login


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserError(
    @Expose
    @SerializedName("name")
    var name: List<String?>?,

    @Expose
    @SerializedName("email")
    var email: List<String?>?,

    @Expose
    @SerializedName("address")
    var address: List<String?>?,

    @Expose
    @SerializedName("phone_number")
    var phoneNumber: List<String?>?,

    @Expose
    @SerializedName("house_number")
    var houseNumber: List<String?>?,

    @Expose
    @SerializedName("city")
    var city: List<String?>?,

    @Expose
    @SerializedName("password")
    var password: List<String?>?,

    @Expose()
    @SerializedName("profile_photo_path")
    val profile_photo_path: String,

    @Expose()
    @SerializedName("profile_photo_url")
    val profile_photo_url: String,
)