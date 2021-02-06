package com.app.srestaurantapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.model.DishesDataDetails
import com.google.gson.Gson
import kotlinx.android.synthetic.main.content_main.*

class AboutUs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        setSupportActionBar(tool_bar)


    }
}