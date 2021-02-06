package com.app.srestaurantapplication.ui.registeration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.util.navigateToSignIn
import com.app.srestaurantapplication.util.navigateToSignUp
import kotlinx.android.synthetic.main.activity_registeration.*

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration)

        signIn_registrationActivity_button.setOnClickListener {
            navigateToSignIn()
        }


        sign_up_registrationActivity_button.setOnClickListener {
            navigateToSignUp()
        }


    }
}