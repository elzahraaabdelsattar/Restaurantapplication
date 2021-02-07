package com.app.srestaurantapplication.ui.offers

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
import com.app.srestaurantapplication.data.model.ResponsOffers
import com.app.srestaurantapplication.data.model.ResponseDto
import com.app.srestaurantapplication.data.remote.RetrofitClient
import com.app.srestaurantapplication.ui.foodMenu.FoodMenuAdapter
import com.app.srestaurantapplication.util.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_bag.*
import kotlinx.android.synthetic.main.activity_food_menu.*
import kotlinx.android.synthetic.main.activity_offers.*
import kotlinx.android.synthetic.main.content_bag.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_offers.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OffersActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toggle:ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offers)

        init()
        getOffers()
    }

    private fun getOffers() {

        offers_progressBar.show()
        val prefManager= PrefManager(this)
        val token="Bearer".plus(prefManager.retrieveString(TOKEN))

        RetrofitClient.instance.getOffers(token).enqueue(object:Callback<ResponseDto<ResponsOffers>?>{
            override fun onFailure(call: Call<ResponseDto<ResponsOffers>?>, t: Throwable) {

                offers_progressBar.hide()
                Toast.makeText(this@OffersActivity,t.message.toString(),Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<ResponseDto<ResponsOffers>?>,
                response: Response<ResponseDto<ResponsOffers>?>
            ) {
                if (response.code()==200)
                {
                    if (response.isSuccessful)
                    {
                        offers_progressBar.hide()
                        val offersList=response.body()!!.data!!.data
                        val offersAdapter = OffersAdapter(offersList,this@OffersActivity)
                        //////


                        recycler_view_offers.layoutManager = LinearLayoutManager(this@OffersActivity, RecyclerView.VERTICAL, false)
                        recycler_view_offers.setHasFixedSize(true)
                        recycler_view_offers.addItemDecoration(DividerItemDecoration(this@OffersActivity, DividerItemDecoration.VERTICAL))

                        recycler_view_offers.adapter = offersAdapter
                    }
                }

                else{

                    offers_progressBar.hide()
                    Toast.makeText(this@OffersActivity,"Error In Data".toString(),Toast.LENGTH_LONG).show()

                }
            }
        })
    }

    private fun init() {

        setSupportActionBar(tool_bar_offers)
        toggle= ActionBarDrawerToggle(this,drawer_layout_offers,R.string.open,R.string.close)
        drawer_layout_offers.addDrawerListener(toggle)
        toggle.syncState()
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white_color));
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nav_view_offers.setNavigationItemSelectedListener(this);


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
        drawer_layout_offers.closeDrawer(GravityCompat.START)
        return true

    }
}