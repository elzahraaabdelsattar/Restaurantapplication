package com.app.srestaurantapplication.data.model

import com.google.gson.annotations.SerializedName

data class ResponseOrderPrice (

    @SerializedName("shipping_fees")
    val shipping_fees: Int,

    @SerializedName("sub_total")
    val sub_total:Int,


    @SerializedName("total_price")
    val total_price:Int
)