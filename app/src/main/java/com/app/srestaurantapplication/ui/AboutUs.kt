package com.app.srestaurantapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.model.DishesDataDetails
import com.app.srestaurantapplication.util.*
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.activity_food_menu.*
import kotlinx.android.synthetic.main.content_about.*
import kotlinx.android.synthetic.main.content_main.*

class AboutUs : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener{

    lateinit var toggle:ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        init()
    }

    private fun init() {
        ////
        setSupportActionBar(tool_bar_aboutRes)
        toggle= ActionBarDrawerToggle(this,drawer_layout_about,R.string.open,R.string.close)
        drawer_layout_about.addDrawerListener(toggle)
        toggle.syncState()
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white_color));
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nav_view_about.setNavigationItemSelectedListener(this);


        /////
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when(item.itemId){
                R.id.nav_home->
                {
                    navigateToFoodMenuActivity()
                }
                R.id.nav_bag->
                {
                    navigateToBag()
                }
                R.id.nav_offers->
                {
                    navigateToOffers()
                }
                R.id.nav_reservation->
                {
                    navigateToReservation()
                }
                R.id.nav_myorders->
                {
                    navigateToMyOrders()
                }
                R.id.nav_suggestion->
                {
                    navigateToComplaint()
                }
                R.id.nav_review->
                {
                    navigateToReviewActivity()
                }
                R.id.nav_about_us->
                {
                    navigateToBoutUs()
                }
                R.id.nav_branches->
                {
                    navigateToBranches()
                }

            }
        drawer_layout_about.closeDrawer(GravityCompat.START)
            return true

        }
    }


