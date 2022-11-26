package com.ngemeal.ngemeal.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Wrapper <T>(
    @Expose
    @SerializedName("meta")
    val meta: Meta ? = null,

    @Expose
    @SerializedName("data")
    val `data`: T
)