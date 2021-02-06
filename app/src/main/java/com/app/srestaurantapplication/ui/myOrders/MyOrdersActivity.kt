package com.app.srestaurantapplication.ui.myOrders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.local.PrefManager
import com.app.srestaurantapplication.data.local.TOKEN
import com.app.srestaurantapplication.data.model.BagSharedPrefrences
import com.app.srestaurantapplication.data.model.ResponseDto
import com.app.srestaurantapplication.data.model.ResponseMyOrders
import com.app.srestaurantapplication.data.remote.RetrofitClient
import com.app.srestaurantapplication.ui.bag.BagAdapter
import com.app.srestaurantapplication.util.hide
import com.app.srestaurantapplication.util.show
import kotlinx.android.synthetic.main.activity_bag.*
import kotlinx.android.synthetic.main.activity_my_orders.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyOrdersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_orders)

        getMyOrders()
    }

    private fun getMyOrders() {

        my_orders_progressBar.show()
        val prefManager= PrefManager(this)
        val token="Bearer".plus(prefManager.retrieveString(TOKEN))

        RetrofitClient.instance.myOrders(token).enqueue(object:Callback<ResponseDto<MutableList<ResponseMyOrders>?>?>{
            override fun onFailure(
                call: Call<ResponseDto<MutableList<ResponseMyOrders>?>?>,
                t: Throwable
            ) {
                my_orders_progressBar.hide()
                Toast.makeText(this@MyOrdersActivity,t.message,Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<ResponseDto<MutableList<ResponseMyOrders>?>?>,
                response: Response<ResponseDto<MutableList<ResponseMyOrders>?>?>
            )
            {
                if (response.code()==200)
                {
                    if (response.isSuccessful)
                    {
                        my_orders_progressBar.hide()

                        val myOrdersAdapter =
                            MyOrdersAdapter(response.body()!!.data)

                        rv_myOrdersActivity.layoutManager = LinearLayoutManager(this@MyOrdersActivity, RecyclerView.VERTICAL, false)
                        // (recycler_view_main.layoutManager as LinearLayoutManager).reverseLayout = true
                        rv_myOrdersActivity.setHasFixedSize(true)
                        rv_myOrdersActivity.addItemDecoration(DividerItemDecoration(this@MyOrdersActivity, DividerItemDecoration.VERTICAL))

                        rv_myOrdersActivity.adapter = myOrdersAdapter
                    }
                    else{
                        my_orders_progressBar.hide()
                        Toast.makeText(this@MyOrdersActivity,"Error In Data",Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}