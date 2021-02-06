package com.app.srestaurantapplication.ui.registeration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        sign_up_signUpActivity_button.setOnClickListener {

            signUp()
        }
    }

    private fun signUp() {

        sign_up_progressBar.show()

        //getData
        val name= name_signUpActivity_editText.text.toString().trim()
        val phone= phone_signUpActivity_editText.text.toString().trim()
        val password= password_signUpActivity_editText.text.toString().trim()
        val confirmPassword= cpassword_SignUpActivity_editText.text.toString().trim()


        //validation
        var valid = true

        if (name.isEmpty())
        {
            sign_up_progressBar.show()
            name_signUpActivity_editText.error = "Invalid Data"
            valid = false
        }
        if (phone.isEmpty())
        {
            sign_up_progressBar.show()
            phone_signUpActivity_editText.error = "Invalid Data"
            valid = false
        }

        if (password.isEmpty())
        {
            sign_up_progressBar.show()
            password_signUpActivity_editText.error = "Invalid Data"
            valid = false
        }
        if (confirmPassword.isEmpty())
        {
            sign_up_progressBar.show()
            cpassword_SignUpActivity_editText.error = "Enter Your Password"
            valid = false
        }

        if (!valid) {
            return
        }

        //callApi
        RetrofitClient.instance.signUp(name,phone,password,confirmPassword).enqueue(object:Callback<ResponseDto<RegisterationResponse>?>{
            override fun onFailure(call: Call<ResponseDto<RegisterationResponse>?>, t: Throwable) {
                sign_up_progressBar.hide()
                Toast.makeText(this@SignUpActivity,t.message.toString(),Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<ResponseDto<RegisterationResponse>?>,
                response: Response<ResponseDto<RegisterationResponse>?>
            ) {
                if (response.code() == 200) {

                    if(response.body()!!.success)
                    {
                        //saveToken
                        val prefManager= PrefManager(this@SignUpActivity)
                        prefManager.saveString(TOKEN,response.body()!!.data!!.token)
                        sign_up_progressBar.hide()
                        ///navigate
                        navigateToFoodMenuActivity()
                    }

                }
                else{
                    sign_up_progressBar.hide()
                    Toast.makeText(this@SignUpActivity,"Error In Data".toString(),Toast.LENGTH_LONG).show()

                }            }
        })
    }
}