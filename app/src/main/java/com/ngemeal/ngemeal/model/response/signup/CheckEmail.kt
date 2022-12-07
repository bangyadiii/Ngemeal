package com.ngemeal.ngemeal.model.response.signup


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CheckEmail(
    @Expose
    @SerializedName("is_email_available")
    var isEmailAvailable: Boolean?
)