package com.app.srestaurantapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.local.PrefManager
import com.app.srestaurantapplication.data.local.TOKEN
import com.app.srestaurantapplication.data.model.CategoriesList
import com.app.srestaurantapplication.data.model.ResponseDto
import com.app.srestaurantapplication.data.remote.RetrofitClient
import com.app.srestaurantapplication.ui.foodMenu.FoodMenuAdapter
import com.app.srestaurantapplication.ui.orderDescription.OrderDescriptionActivity
import com.app.srestaurantapplication.util.hide
import com.app.srestaurantapplication.util.show
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_reservation.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReservationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        send_reservationActivity_button.setOnClickListener {
            makeReservation()


        }

    }

    private fun makeReservation() {

        reservation_progressBar.show()
        val prefManager= PrefManager(this)
        val token="Bearer".plus(prefManager.retrieveString(TOKEN))
        val name=name_reservationActivity_editText.text.trim().toString()
        val phone=phone_reservationActivity_editText.text.trim().toString()

        //validation
        var valid = true

        if (name.isEmpty())
        {
            reservation_progressBar.hide()
            name_reservationActivity_editText.error = "Invalid Data"
            valid = false
        }
        if (phone.isEmpty())
        {
            reservation_progressBar.hide()
            phone_reservationActivity_editText.error = "Enter Your Password"
            valid = false
        }

        if (!valid) {
            return
        }

        RetrofitClient.instance.makeReservation(token,name,phone).enqueue(object:Callback<ResponseDto<String>?>{
            override fun onFailure(call: Call<ResponseDto<String>?>, t: Throwable) {

                reservation_progressBar.hide()
                Toast.makeText(this@ReservationActivity,t.message.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<ResponseDto<String>?>,
                response: Response<ResponseDto<String>?>
            ) {
                if (response.code()==200)
                {
                    if (response.body()!!.success)
                    {
                        reservation_progressBar.hide()
                        Toast.makeText(this@ReservationActivity,response.body()!!.data.toString(), Toast.LENGTH_LONG).show()
                    }

                }
                else{
                    reservation_progressBar.hide()
                    Toast.makeText(this@ReservationActivity,"Error In Data", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}