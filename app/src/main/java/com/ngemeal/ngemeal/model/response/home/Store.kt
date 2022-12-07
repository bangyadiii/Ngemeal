package com.ngemeal.ngemeal.model.response.home


import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Store(
    @Expose
    @SerializedName("id")
    val id: Int?,
    @Expose
    @SerializedName("user_id")
    val userId: String?,
    @Expose
    @SerializedName("store_name")
    val storeName: String?,
    @Expose
    @SerializedName("address")
    val address: String?,
    @Expose
    @SerializedName("description")
    val description: String?,
    @Expose
    @SerializedName("rekening_number")
    val rekeningNumber: String?,
    @Expose
    @SerializedName("logo_path")
    val logoPath: String?,
    @Expose
    @SerializedName("deleted_at")
    val deletedAt: Long?,
    @Expose
    @SerializedName("created_at")
    val createdAt: Long?,
    @Expose
    @SerializedName("updated_at")
    val updatedAt: Long?
) : Parcelable