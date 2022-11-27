package com.ngemeal.ngemeal.model.response.home


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Data(
    @Expose
    @SerializedName("id")
    val id: Int?,
    @Expose
    @SerializedName("store_id")
    val storeId: Int?,
    @Expose
    @SerializedName("name")
    val name: String?,
    @Expose
    @SerializedName("description")
    val description: String?,

    @Expose
    @SerializedName("ingredients")
    val ingredients: String?,
    @Expose
    @SerializedName("price")
    val price: Int?,
    @Expose
    @SerializedName("rate")
    val rate: Float?,
    @Expose
    @SerializedName("types")
    val types: String?,
    @Expose
    @SerializedName("archived")
    val archived: Any?,
    @Expose
    @SerializedName("deleted_at")
    val deletedAt: Any?,
    @Expose
    @SerializedName("created_at")
    val createdAt: Int?,
    @Expose
    @SerializedName("updated_at")
    val updatedAt: Int?,
    @Expose
    @SerializedName("images")
    val images: List<Image?>?,
    @Expose
    @SerializedName("store")
    val store: Store?
)