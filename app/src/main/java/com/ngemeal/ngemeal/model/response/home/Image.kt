package com.ngemeal.ngemeal.model.response.home


import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
    @Expose
    @SerializedName("id")
    val id: Int?,
    @Expose
    @SerializedName("food_id")
    val foodId: Int?,
    @Expose
    @SerializedName("image_path")
    val imagePath: String?,
    @Expose
    @SerializedName("image_url")
    val imageUrl: String?,
    @Expose
    @SerializedName("is_primary")
    val isPrimary: Int?
) : Parcelable