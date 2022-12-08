package com.ngemeal.ngemeal.model.response.transaction


import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ngemeal.ngemeal.model.response.login.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Transaction(
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
    @SerializedName("total")
    val total: Int?,
    @Expose
    @SerializedName("quantity")
    val quantity: Int?,
    @Expose
    @SerializedName("trx_status")
    val trxStatus: String?,
    @Expose
    @SerializedName("delivery_status")
    val deliveryStatus: String?,
    @Expose
    @SerializedName("payment_url")
    val paymentUrl: String?,
    @Expose
    @SerializedName("md_snap_token")
    val mdSnapToken: String?,
    @Expose
    @SerializedName("snap_token")
    val snapToken: String?,
    @Expose
    @SerializedName("metadata")
    val metadata: String?,
    @Expose
    @SerializedName("created_at")
    val createdAt: Long?,
    @Expose
    @SerializedName("updated_at")
    val updatedAt: Long?,
    @Expose
    @SerializedName("user")
    val user: User?,
    @Expose
    @SerializedName("food")
    val food: Food?
) : Parcelable
