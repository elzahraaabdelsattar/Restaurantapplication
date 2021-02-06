package com.app.srestaurantapplication.ui.check

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.local.PrefManager
import com.app.srestaurantapplication.data.local.TOKEN
import com.app.srestaurantapplication.data.model.BagSharedPrefrences
import com.app.srestaurantapplication.data.model.OrderDetails
import com.app.srestaurantapplication.data.model.ResponseDto
import com.app.srestaurantapplication.data.model.ResponseOrderPrice
import com.app.srestaurantapplication.data.remote.RetrofitClient
import com.app.srestaurantapplication.ui.bag.BagAdapter
import kotlinx.android.synthetic.main.activity_check.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckActivity : AppCompatActivity() {

    var idList: MutableList<Int> = mutableListOf()
    var quantityList: MutableList<Int> = mutableListOf()
    var mainProductList: MutableList<Int> = mutableListOf()
    var totalPrice: Int = 0
    var quantity  = mutableListOf<Int>()
    var mainProduct = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check)

        /////////////////////
        setCheck()
        total_orders_checkActivity_textView.text =
            BagSharedPrefrences.retreiveBagData().size.toString()

        ///////////////////////////

        val flag = intent.getStringExtra("flag")

        if (flag.equals("delivery")) {
            for (i in 0 until BagSharedPrefrences.retreiveBagData().size) {
                idList.add(BagSharedPrefrences.retreiveBagData()[i].id)
                quantityList.add(BagSharedPrefrences.retreiveBagData()[i].counter)
                mainProductList.add(BagSharedPrefrences.retreiveBagData()[i].MainItem)

            }

            quantity = quantityList
            mainProduct = mainProductList

            orderPrice()
        } else {
            for (i in 0 until BagSharedPrefrences.retreiveBagData().size )
            {
                totalPrice += (BagSharedPrefrences.retreiveBagData()[i].counter) * (BagSharedPrefrences.retreiveBagData()[i].price.toInt())

            }
            total_bill_checkActivity_textView.text = totalPrice.toString()
        }

    }

    private fun orderPrice() {

        val prefManager = PrefManager(this)
        val token = "Bearer ".plus(prefManager.retrieveString(TOKEN))
        val cityId = prefManager.retrieveString("city_id")


        RetrofitClient.instance.getOrderPrice(
            "application/json",
            token,
            OrderDetails(cityId.toString(), idList, quantityList, mainProduct)
        )
            .enqueue(object : Callback<ResponseDto<ResponseOrderPrice>?> {
                override fun onFailure(call: Call<ResponseDto<ResponseOrderPrice>?>, t: Throwable) {
                    Log.d("error", "error", t)
                }

                override fun onResponse(
                    call: Call<ResponseDto<ResponseOrderPrice>?>,
                    response: Response<ResponseDto<ResponseOrderPrice>?>
                ) {
                    if (response.code()==200){
                        if(response.isSuccessful){
                            shipping_fees_checkActivity_linear_layout.visibility=View.VISIBLE
                            group_checkActivity.visibility=View.VISIBLE
                            PPrice_checkActivity_textView.text=response.body()!!.data!!.sub_total.toString()
                            shipping_fees_checkActivity_textView.text=response.body()!!.data!!.shipping_fees.toString()
                            total_bill_checkActivity_textView.text=response.body()!!.data!!.total_price.toString()
                        }
                    }
                }
            })

    }

    private fun setCheck() {
        val bagAdapter =
            BagAdapter(BagSharedPrefrences.retreiveBagData(), this, 1)

        rv_checkActivity.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        // (recycler_view_main.layoutManager as LinearLayoutManager).reverseLayout = true
        rv_checkActivity.setHasFixedSize(true)
        rv_checkActivity.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        rv_checkActivity.adapter = bagAdapter
    }
}