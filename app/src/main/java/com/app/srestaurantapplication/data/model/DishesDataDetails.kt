package com.app.srestaurantapplication.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DishesDataDetails(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String?,

    @SerializedName("image")
    val image: String?,

    @SerializedName("image_url")
    val image_url: String?,

    @SerializedName("video")
    val video: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("price")
    val price: Double,

    @SerializedName("category_id")
    val category_id: Int,

    @SerializedName("deleted")
    val deleted:Int,

    @SerializedName("type")
    val type: String?,

    @SerializedName("created_at")
    val created_at: String?,

    @SerializedName("updated_at")
    val updated_at: String?,


    @SerializedName("category")
    val category: Category?,

    @SerializedName("product_details")
    val productDetails: List<ProductDetail>?


):Parcelable