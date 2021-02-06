package com.app.srestaurantapplication.ui.offers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.local.PrefManager
import com.app.srestaurantapplication.data.local.TOKEN
import com.app.srestaurantapplication.data.model.ResponsOffers
import com.app.srestaurantapplication.data.model.ResponseDto
import com.app.srestaurantapplication.data.remote.RetrofitClient
import com.app.srestaurantapplication.ui.foodMenu.FoodMenuAdapter
import com.app.srestaurantapplication.util.hide
import com.app.srestaurantapplication.util.show
import kotlinx.android.synthetic.main.activity_offers.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OffersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offers)

        getOffers()
    }

    private fun getOffers() {

        offers_progressBar.show()
        val prefManager= PrefManager(this)
        val token="Bearer".plus(prefManager.retrieveString(TOKEN))

        RetrofitClient.instance.getOffers(token).enqueue(object:Callback<ResponseDto<ResponsOffers>?>{
            override fun onFailure(call: Call<ResponseDto<ResponsOffers>?>, t: Throwable) {

                offers_progressBar.hide()
                Toast.makeText(this@OffersActivity,t.message.toString(),Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<ResponseDto<ResponsOffers>?>,
                response: Response<ResponseDto<ResponsOffers>?>
            ) {
                if (response.code()==200)
                {
                    if (response.isSuccessful)
                    {
                        offers_progressBar.hide()
                        val offersList=response.body()!!.data!!.data
                        val offersAdapter = OffersAdapter(offersList,this@OffersActivity)
                        //////


                        recycler_view_offers.layoutManager = LinearLayoutManager(this@OffersActivity, RecyclerView.VERTICAL, false)
                        recycler_view_offers.setHasFixedSize(true)
                        recycler_view_offers.addItemDecoration(DividerItemDecoration(this@OffersActivity, DividerItemDecoration.VERTICAL))

                        recycler_view_offers.adapter = offersAdapter
                    }
                }

                else{

                    offers_progressBar.hide()
                    Toast.makeText(this@OffersActivity,"Error In Data".toString(),Toast.LENGTH_LONG).show()

                }
            }
        })
    }
}