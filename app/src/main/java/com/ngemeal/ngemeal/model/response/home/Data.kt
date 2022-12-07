package com.ngemeal.ngemeal.model.response.home


import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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
    val price: Int,
    @Expose
    @SerializedName("rate")
    val rate: Float?,
    @Expose
    @SerializedName("types")
    val types: String?,
    @Expose
    @SerializedName("archived")
    val archived: Long?,
    @Expose
    @SerializedName("deleted_at")
    val deletedAt: Long?,
    @Expose
    @SerializedName("created_at")
    val createdAt: Long?,
    @Expose
    @SerializedName("updated_at")
    val updatedAt: Long?,
    @Expose
    @SerializedName("images")
    val images: List<Image?>?,
    @Expose
    @SerializedName("store")
    val store: Store?
) : Parcelable