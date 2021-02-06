package com.app.srestaurantapplication.data.remote

import com.app.srestaurantapplication.data.model.*
import retrofit2.Call
import retrofit2.http.*

interface APIClient {

    //signIn
    @POST("login")
    @FormUrlEncoded
    fun signIn(
        @Field("phone") phone: String,
        @Field("password") password: String
    ): Call<ResponseDto<RegisterationResponse>?>

    //signUp
    @POST("register")
    @FormUrlEncoded
    fun signUp(
        @Field("username") username: String,
        @Field("phone") phone: String,
        @Field("password") password: String,
        @Field("re_password") re_password: String
    ): Call<ResponseDto<RegisterationResponse>?>

    //reservation
    @POST("make_reservation")
    @FormUrlEncoded
    fun makeReservation(
        @Header("Authorization") token: String,
        @Field("name") name: String,
        @Field("phone") email: String
    ): Call<ResponseDto<String>?>


    //sendS_C
    @POST("send_c_and_s")
    @FormUrlEncoded
    fun s_c(
        @Header("Authorization") token: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("message") message: String,
        @Field("phone") phone: String
    ): Call<ResponseDto<String>?>

    //sendRate
    @POST("send_rate")
    @FormUrlEncoded
    fun sendRate(
        @Header("Authorization") token: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("rate") rate: String
    ): Call<ResponseDto<String>?>

    //get_categories
    @GET("all_categories")
    fun getCategories(
        @Header("Authorization") token: String
    ): Call<ResponseDto<MutableList<CategoriesList>>>

    //get_all_products
    @POST("all_products")
    @FormUrlEncoded
    fun getAllProducts(
        @Header("Authorization") token: String,
        @Field("page") page: Int,
        @Field("category_id") category_id: Int
    ): Call<ResponseDto<DishesData>?>


    //get_cities
    @GET("all_cities")
    fun getCities(
        @Header("Authorization") token: String
    ): Call<ResponseDto<MutableList<CityModel>?>?>

    //get_all_products
    @POST("order_price")
    fun getOrderPrice(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") token: String,
        @Body orderDetails: OrderDetails
    ): Call<ResponseDto<ResponseOrderPrice>?>

    //get_offers
    @GET("offers")
    fun getOffers(
        @Header("Authorization") token: String
    ): Call<ResponseDto<ResponsOffers>?>

    //get_cities
    @GET("close_open")
    fun getClosedOpen(
        @Header("Authorization") token: String
    ): Call<ResponseDto<Int>>

    //log_out
    @POST("logout")
    fun logOut(
        @Header("Authorization") token: String
    ): Call<ResponseDto<String>>

    //get_my_orders
    @GET("my_orders")
    fun myOrders(
        @Header("Authorization") token: String
    ): Call<ResponseDto<MutableList<ResponseMyOrders>?>?>


}