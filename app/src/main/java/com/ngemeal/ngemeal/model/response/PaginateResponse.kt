package com.ngemeal.ngemeal.model.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PaginateResponse<T>(
    @Expose
    @SerializedName("current_page")
    val currentPage: Int?,
    @Expose
    @SerializedName("data")
    var `data`: List<T>,
    @Expose
    @SerializedName("first_page_url")
    val firstPageUrl: String?,
    @Expose
    @SerializedName("from")
    val from: Int?,
    @Expose
    @SerializedName("last_page")
    val lastPage: Int?,
    @Expose
    @SerializedName("last_page_url")
    val lastPageUrl: String?,
    @Expose
    @SerializedName("links")
    val links: List<PaginateLinks?>?,
    @Expose
    @SerializedName("next_page_url")
    val nextPageUrl: String?,
    @Expose
    @SerializedName("path")
    val path: String?,
    @Expose
    @SerializedName("per_page")
    val perPage: Int?,
    @Expose
    @SerializedName("prev_page_url")
    val prevPageUrl: Any?,
    @Expose
    @SerializedName("to")
    val to: Int?,
    @Expose
    @SerializedName("total")
    val total: Int?
)