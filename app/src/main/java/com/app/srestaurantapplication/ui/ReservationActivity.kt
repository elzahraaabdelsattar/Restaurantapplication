package com.app.srestaurantapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
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
import com.app.srestaurantapplication.util.*
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_bag.*
import kotlinx.android.synthetic.main.activity_food_menu.*
import kotlinx.android.synthetic.main.activity_reservation.*
import kotlinx.android.synthetic.main.content_bag.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_reservation.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReservationActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        init()

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

    private fun init() {

        setSupportActionBar(tool_bar_reservation)
        toggle= ActionBarDrawerToggle(this,drawer_layout_reservation,R.string.open,R.string.close)
        drawer_layout_reservation.addDrawerListener(toggle)
        toggle.syncState()
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white_color));
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nav_view_reservation.setNavigationItemSelectedListener(this);


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
        drawer_layout_reservation.closeDrawer(GravityCompat.START)
        return true

    }
}