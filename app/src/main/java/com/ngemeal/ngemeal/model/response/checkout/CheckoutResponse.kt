package com.ngemeal.ngemeal.model.response.checkout


import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CheckoutResponse(
    @Expose
    @SerializedName("id")
    val id: String?,

    @Expose
    @SerializedName("user_id")
    val userId: String?,

    @Expose
    @SerializedName("food_id")
    val foodId: Int?,
    @Expose
    @SerializedName("order_id")
    val orderID: String?,
    @Expose
    @SerializedName("total")
    val total: Int?,

    @Expose
    @SerializedName("quantity")
    val quantity: Int?,

    @Expose
    @SerializedName("status")
    val status: String?,

    @Expose
    @SerializedName("payment_url")
    val paymentUrl: String?,

    @Expose
    @SerializedName("snap_token")
    val snapToken: String?,

    @Expose
    @SerializedName("md_snap_token")
    val mdSnapToken: String?,
    @Expose
    @SerializedName("metadata")
    val metadata: String?,

    @Expose
    @SerializedName("created_at")
    val createdAt: Long?,

    @Expose
    @SerializedName("updated_at")
    val updatedAt: Long?
) : Parcelable