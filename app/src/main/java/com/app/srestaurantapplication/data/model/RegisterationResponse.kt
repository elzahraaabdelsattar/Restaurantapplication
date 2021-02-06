package com.app.srestaurantapplication.data.model

import com.google.gson.annotations.SerializedName

data class RegisterationResponse (

    @SerializedName("id")
    val id: Int,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("token")
    val token: String
)
