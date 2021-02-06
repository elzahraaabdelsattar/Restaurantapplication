package com.app.srestaurantapplication.ui.bag

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.local.PrefManager
import com.app.srestaurantapplication.data.model.BagSharedPrefrences
import com.app.srestaurantapplication.ui.foodMenu.DetailsFoodMenuAdapter
import com.app.srestaurantapplication.util.navigateToCheckActivity
import com.app.srestaurantapplication.util.navigateToLocationActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_bag.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.deliver_way.*
import kotlinx.android.synthetic.main.deliver_way.view.*

class Bag : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bag)


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
}