package com.ngemeal.ngemeal.model.response.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Roles(
    @Expose
    @SerializedName("id")
    val id : Int,

    @Expose
    @SerializedName("roles")
    val slug : String
)
