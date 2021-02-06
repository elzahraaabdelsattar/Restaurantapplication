package com.app.srestaurantapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.local.PrefManager
import com.app.srestaurantapplication.data.local.TOKEN
import com.app.srestaurantapplication.data.model.ResponseDto
import com.app.srestaurantapplication.data.remote.RetrofitClient
import com.app.srestaurantapplication.util.hide
import com.app.srestaurantapplication.util.isValidEmail
import com.app.srestaurantapplication.util.show
import kotlinx.android.synthetic.main.activity_complaints.*
import kotlinx.android.synthetic.main.activity_reservation.*
import kotlinx.android.synthetic.main.activity_review.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)





        send_reviewActivity_button.setOnClickListener {
            sendRate()
        }

    }

    private fun sendRate() {

        review_progressBar.show()

        val prefManager= PrefManager(this)
        val token="Bearer".plus(prefManager.retrieveString(TOKEN))
        val rate=rating_bar.rating.toInt().toString()
        val email=editTextTextEmailAddress.text.trim().toString()
        val name=editTextTextPersonName.text.trim().toString()


        //validation
        var valid = true

        if (!email.isValidEmail())
        {
            review_progressBar.hide()
            editTextTextEmailAddress.error = "Email Is Required"
            valid = false
        }

        if (name.isEmpty())
        {
            review_progressBar.hide()
            editTextTextPersonName.error = "Name Is Required"
            valid = false
        }

        if (!valid) {
            return
        }

        RetrofitClient.instance.sendRate(token,name,email,rate).enqueue(object:Callback<ResponseDto<String>?>{
            override fun onFailure(call: Call<ResponseDto<String>?>, t: Throwable) {

                review_progressBar.hide()
                Toast.makeText(this@ReviewActivity,t.message.toString(),Toast.LENGTH_LONG).show()

            }

            override fun onResponse(
                call: Call<ResponseDto<String>?>,
                response: Response<ResponseDto<String>?>
            ) {
                if(response.code()==200)
                {
                    if(response.body()!!.success)
                    {
                        review_progressBar.hide()
                        Toast.makeText(this@ReviewActivity,response.body()!!.data,Toast.LENGTH_LONG).show()

                    }
                }
                else{
                    review_progressBar.hide()
                    Toast.makeText(this@ReviewActivity,"Error In Data", Toast.LENGTH_LONG).show()
                }

            }
        })

    }


}
