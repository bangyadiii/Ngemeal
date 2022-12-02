package com.ngemeal.ngemeal.model.response.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @Expose()
    @SerializedName("id")
    val id: String,

    @Expose()
    @SerializedName("name")
    val name: String,

    @Expose()
    @SerializedName("email")
    val email: String,

    @Expose()
    @SerializedName("email_verified_at")
    val email_verified_at: String,

    @Expose()
    @SerializedName("address")
    val address: String,

    @Expose()
    @SerializedName("city")
    val city: String,

    @Expose()
    @SerializedName("house_number")
    val house_number: String,

    @Expose()
    @SerializedName("phone_number")
    val phone_number: String,

    @Expose()
    @SerializedName("profile_photo_path")
    val profile_photo_path: String,

    @Expose()
    @SerializedName("profile_photo_url")
    val profile_photo_url: String,

    @Expose()
    @SerializedName("role_id")
    val roleId: Int,

    @Expose()
    @SerializedName("two_factor_confirmed_at")
    val two_factor_confirmed_at: String,

    @Expose()
    @SerializedName("created_at")
    val created_at: Int,

    @Expose()
    @SerializedName("updated_at")
    val updated_at: Int
)