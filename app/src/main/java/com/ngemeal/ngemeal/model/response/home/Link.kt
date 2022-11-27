package com.ngemeal.ngemeal.model.response.home


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Link(
    @Expose
    @SerializedName("url")
    val url: String?,
    @Expose
    @SerializedName("label")
    val label: String?,
    @Expose
    @SerializedName("active")
    val active: Boolean?
)