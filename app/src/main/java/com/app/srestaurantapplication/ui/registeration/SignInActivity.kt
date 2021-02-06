package com.app.srestaurantapplication.ui.registeration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.local.PrefManager
import com.app.srestaurantapplication.data.local.TOKEN
import com.app.srestaurantapplication.data.model.RegisterationResponse
import com.app.srestaurantapplication.data.model.ResponseDto
import com.app.srestaurantapplication.data.remote.RetrofitClient
import com.app.srestaurantapplication.util.hide
import com.app.srestaurantapplication.util.navigateToFoodMenuActivity
import com.app.srestaurantapplication.util.show
import kotlinx.android.synthetic.main.activity_reservation.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        sign_in_signInActivity_button.setOnClickListener {

            logIn()
        }
    }

    private fun logIn() {

        sign_in_progressBar.show()

        //getData
        val phone= phone_signInActivity_editText.text.toString().trim()
        val password= password_signInActivity_editText.text.toString().trim()

        //validation
        var valid = true

        if (password.isEmpty())
        {
            sign_in_progressBar.hide()
            password_signInActivity_editText.error = "Invalid Data"
            valid = false
        }
        if (phone.isEmpty())
        {
            sign_in_progressBar.hide()
            phone_signInActivity_editText.error = "Enter Your Password"
            valid = false
        }

        if (!valid) {
            return
        }

        RetrofitClient.instance.signIn(phone,password).enqueue(object:Callback<ResponseDto<RegisterationResponse>?>{
            override fun onFailure(call: Call<ResponseDto<RegisterationResponse>?>, t: Throwable) {
                sign_in_progressBar.hide()
                Toast.makeText(this@SignInActivity,t.message.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<ResponseDto<RegisterationResponse>?>,
                response: Response<ResponseDto<RegisterationResponse>?>
            ) {
                if (response.code() == 200) {

                    if(response.body()!!.success)
                    {
                        Log.d("token",response.body()!!.data!!.token)
                        //saveToken
                        val prefManager= PrefManager(this@SignInActivity)
                        prefManager.saveString(TOKEN,response.body()!!.data!!.token)
                        sign_in_progressBar.hide()
                        ///navigate
                        navigateToFoodMenuActivity()
                    }

                }
                else{
                    sign_in_progressBar.hide()
                    Toast.makeText(this@SignInActivity,"Error In Data".toString(),Toast.LENGTH_LONG).show()

                }

            }
        })

    }
}