package com.ngemeal.ngemeal.model.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PaginateLinks(
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