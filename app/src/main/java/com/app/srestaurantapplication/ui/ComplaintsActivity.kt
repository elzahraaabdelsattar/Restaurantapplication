package com.app.srestaurantapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.local.PrefManager
import com.app.srestaurantapplication.data.local.TOKEN
import com.app.srestaurantapplication.data.model.ComplaintAndSuggestion
import com.app.srestaurantapplication.data.model.ResponseDto
import com.app.srestaurantapplication.data.remote.RetrofitClient
import com.app.srestaurantapplication.util.*
import kotlinx.android.synthetic.main.activity_complaints.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComplaintsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complaints)

        send_complaintActivity_button.setOnClickListener {

            sendComplaint()

        }
    }

    private fun sendComplaint() {

        s_c_progressBar.show()

        val prefManager= PrefManager(this)
        val token="Bearer".plus(prefManager.retrieveString(TOKEN))
        val name=name_complaintActivity_editText.text.toString()
        val email= email_complaintActivity_editText.text.toString()
        val message= complaints_complaintActivity_editText.text.toString().trim()
        val phone=phone_complaintActivity_editText.text.toString().trim()


        var valid = true

        if (!email.isValidEmail())
        {
            s_c_progressBar.hide()
            email_complaintActivity_editText.error = "Invalid Data"
            valid = false
        }
        if (name.isEmpty())
        {
            s_c_progressBar.hide()
            name_complaintActivity_editText.error = "Name Is Required"
            valid = false
        }
        if (phone.isEmpty())
        {
            s_c_progressBar.hide()
            phone_complaintActivity_editText.error = "Phone Is Required"
            valid = false
        }

        if (message.isEmpty())
        {
            s_c_progressBar.hide()
            complaints_complaintActivity_editText.error = "Please Provide Your Complaint Or Your Suggestion"
            valid = false
        }

        if (!valid) {
            return
        }
        RetrofitClient.instance.s_c(token,name,email,message,phone).enqueue(object:Callback<ResponseDto<String>?>{
            override fun onFailure(call: Call<ResponseDto<String>?>, t: Throwable) {

                s_c_progressBar.hide()
                Toast.makeText(this@ComplaintsActivity,t.message.toString(),Toast.LENGTH_LONG).show()


            }

            override fun onResponse(
                call: Call<ResponseDto<String>?>,
                response: Response<ResponseDto<String>?>
            ) {
                if(response.code()==200){
                    if(response.body()!!.success)
                    {
                        s_c_progressBar.hide()
                        Toast.makeText(this@ComplaintsActivity,response.body()!!.data,Toast.LENGTH_LONG).show()

                    }
                    else{
                        s_c_progressBar.hide()
                        Toast.makeText(this@ComplaintsActivity,"Error In Data", Toast.LENGTH_LONG).show()

                    }
                }

            }
        })
    }
}