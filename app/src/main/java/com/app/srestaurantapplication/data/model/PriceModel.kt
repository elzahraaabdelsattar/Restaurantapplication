package com.app.srestaurantapplication.data.model

import com.google.gson.annotations.SerializedName

data class PriceModel (

    @SerializedName("id")
    val id: Int,

    @SerializedName("price")
    val price: Double
)