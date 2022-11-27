package com.ngemeal.ngemeal.model.response.home


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

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
    @SerializedName("is_primary")
    val isPrimary: Int?
)