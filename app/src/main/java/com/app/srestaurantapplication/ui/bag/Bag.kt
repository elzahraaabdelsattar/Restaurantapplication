package com.app.srestaurantapplication.ui.bag

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.local.PrefManager
import com.app.srestaurantapplication.data.model.BagSharedPrefrences
import com.app.srestaurantapplication.ui.foodMenu.DetailsFoodMenuAdapter
import com.app.srestaurantapplication.util.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.activity_bag.*
import kotlinx.android.synthetic.main.activity_food_menu.*
import kotlinx.android.synthetic.main.content_bag.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.deliver_way.*
import kotlinx.android.synthetic.main.deliver_way.view.*

class Bag : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toggle:ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bag)


          init()
          setBag()
        //deliverr
        continue_bagActivity_button.setOnClickListener {

            val bottomSheetDialog = BottomSheetDialog(
                this, R.style.BottomSheetDialogTheme
            )
            val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
                R.layout.deliver_way,
                findViewById<View>(R.id.deliver_way_container) as? ConstraintLayout
            )

            bottomSheetView.from_restaurant_DeliverWay_cardView.setOnClickListener {
                navigateToCheckActivity()
            }

            bottomSheetView.to_home_DeliverWay_cardView.setOnClickListener {
                navigateToLocationActivity()
            }

            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()



        }




    }


    private fun setBag() {
        val bagAdapter =
          BagAdapter(BagSharedPrefrences.retreiveBagData(),this,0)

        rv_bagActivity.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        // (recycler_view_main.layoutManager as LinearLayoutManager).reverseLayout = true
        rv_bagActivity.setHasFixedSize(true)
        rv_bagActivity.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        rv_bagActivity.adapter = bagAdapter
    }

    private fun init() {

        setSupportActionBar(tool_bar_bag)
        toggle= ActionBarDrawerToggle(this,drawer_layout_bag,R.string.open,R.string.close)
        drawer_layout_bag.addDrawerListener(toggle)
        toggle.syncState()
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white_color));
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nav_view_bag.setNavigationItemSelectedListener(this);


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
        drawer_layout_bag.closeDrawer(GravityCompat.START)
        return true

    }
}