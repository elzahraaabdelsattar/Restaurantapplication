package com.app.srestaurantapplication.data.model

import com.google.gson.annotations.SerializedName

data class ResponseMyOrders(
    @SerializedName("id")
    val id: String,

    @SerializedName("total_price")
    val totalPrice: String?,


    @SerializedName("created_at")
    val createdAt: String?
)
