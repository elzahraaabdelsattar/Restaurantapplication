package com.app.srestaurantapplication.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.local.PrefManager
import com.app.srestaurantapplication.data.local.TOKEN
import com.app.srestaurantapplication.util.*
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        splash_screen_image_view.alpha = 0f
        splash_screen_image_view.animate().setDuration(3000).alpha(1f).withEndAction {

//            val prefManager= PrefManager(this)
//            if(prefManager.retrieveString(TOKEN) =="")
//            {
                navigateToRegistration()
//            }
//            else
//            {
//                navigateToFoodMenuActivity()

//            }
        }


      }


    }

