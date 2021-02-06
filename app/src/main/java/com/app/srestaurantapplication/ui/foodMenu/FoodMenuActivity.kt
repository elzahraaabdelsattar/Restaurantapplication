package com.app.srestaurantapplication.ui.foodMenu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.local.PrefManager
import com.app.srestaurantapplication.data.local.TOKEN
import com.app.srestaurantapplication.data.model.CategoriesList
import com.app.srestaurantapplication.data.model.DishesData
import com.app.srestaurantapplication.data.model.DishesDataDetails
import com.app.srestaurantapplication.data.model.ResponseDto
import com.app.srestaurantapplication.data.remote.RetrofitClient
import com.app.srestaurantapplication.ui.orderDescription.OrderDescriptionActivity
import com.app.srestaurantapplication.util.*
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_food_menu.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FoodMenuActivity : AppCompatActivity(),CallBack,NavigationView.OnNavigationItemSelectedListener {


    lateinit var toggle:ActionBarDrawerToggle
    var dishesDetails:List<DishesDataDetails>?=null
    var categoryList: MutableList<CategoriesList>?=null
    var token:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_menu)

        val prefManager= PrefManager(this)
        token="Bearer".plus(prefManager.retrieveString(TOKEN))

        getClosedOpen()
        ////
        setSupportActionBar(tool_bar)
        toggle= ActionBarDrawerToggle(this,drawer_layout,R.string.open,R.string.close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white_color));
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /////
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun getClosedOpen() {
        RetrofitClient.instance.getClosedOpen(token).enqueue(object:Callback<ResponseDto<Int>>{
            override fun onFailure(call: Call<ResponseDto<Int>>, t: Throwable) {
                Toast.makeText(this@FoodMenuActivity,t.message,Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<ResponseDto<Int>>,
                response: Response<ResponseDto<Int>>
            ) {
                if (response.code()==200)
                {
                    if (response.isSuccessful)
                    {
                        if (response.body()!!.data==1)
                        {
                            closed_layout_contenyMain.visibility= View.VISIBLE
                            tool_bar.visibility=View.GONE
                        }
                        else
                        {
                            setSupportActionBar(findViewById(R.id.tool_bar))
                            nav_view.setNavigationItemSelectedListener(this@FoodMenuActivity);
                            getCategories()
                            getAllDishes(1)

                        }
                    }
                }
            }
        })
    }

    private fun getCategories() {



        RetrofitClient.instance.getCategories(token).enqueue(object:Callback<ResponseDto<MutableList<CategoriesList>>>{
            override fun onFailure(
                call: Call<ResponseDto<MutableList<CategoriesList>>>,
                t: Throwable
            ) {
                Toast.makeText(this@FoodMenuActivity,t.message.toString(), Toast.LENGTH_LONG).show()            }

            override fun onResponse(
                call: Call<ResponseDto<MutableList<CategoriesList>>>,
                response: Response<ResponseDto<MutableList<CategoriesList>>>
            ) {
                if (response.code()==200)
                {
                    if (response.body()!!.success)
                    {

                        categoryList= response.body()!!.data
                        val categoryAdapter = FoodMenuAdapter(categoryList,this@FoodMenuActivity,this@FoodMenuActivity)
                        //////


                        recycler_view_main.layoutManager = LinearLayoutManager(this@FoodMenuActivity, RecyclerView.HORIZONTAL, false)
                        recycler_view_main.setHasFixedSize(true)
                        recycler_view_main.addItemDecoration(DividerItemDecoration(this@FoodMenuActivity, DividerItemDecoration.VERTICAL))

                        recycler_view_main.adapter = categoryAdapter

                    }

                    else{

                        Toast.makeText(this@FoodMenuActivity,response.body()?.error,Toast.LENGTH_LONG).show()
                    }
                }            }
        })
    }


    private fun getAllDishes(categoryId:Int) {
        content_main_progressBar.show()

        RetrofitClient.instance.getAllProducts(token,1,categoryId).enqueue(object:Callback<ResponseDto<DishesData>?>{
            override fun onFailure(call: Call<ResponseDto<DishesData>?>, t: Throwable) {
                content_main_progressBar.hide()
                Toast.makeText(this@FoodMenuActivity,t.message.toString(),Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<ResponseDto<DishesData>?>,
                response: Response<ResponseDto<DishesData>?>
            ) {

                if (response.body()!!.success)
                {

                    content_main_progressBar.hide()

                    dishesDetails = response.body()?.data!!.data
                    val detailsFoodMenuAdapter: DetailsFoodMenuAdapter =
                        DetailsFoodMenuAdapter(
                            dishesDetails,
                            this@FoodMenuActivity,
                            this@FoodMenuActivity
                        )
                    //////


                    recycler_view_detailsFoodMenu.layoutManager = LinearLayoutManager(this@FoodMenuActivity, RecyclerView.VERTICAL, false)
                    // (recycler_view_main.layoutManager as LinearLayoutManager).reverseLayout = true
                    recycler_view_detailsFoodMenu.setHasFixedSize(true)
                    recycler_view_detailsFoodMenu.addItemDecoration(DividerItemDecoration(this@FoodMenuActivity, DividerItemDecoration.VERTICAL))

                    recycler_view_detailsFoodMenu.adapter = detailsFoodMenuAdapter

                }

                else{
                    content_main_progressBar.hide()
                    Toast.makeText(this@FoodMenuActivity,response.body()?.error,Toast.LENGTH_LONG).show()
                }            }
        })
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
            R.id.nav_logout->
            {
                logOut()
            }

        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true

    }

    private fun logOut() {
        RetrofitClient.instance.logOut(token).enqueue(object : Callback<ResponseDto<String>> {
            override fun onFailure(call: Call<ResponseDto<String>>, t: Throwable) {
                Toast.makeText(this@FoodMenuActivity,t.message,Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<ResponseDto<String>>,
                response: Response<ResponseDto<String>>
            ) {
                if (response.code()==200)
                {
                    if (response.isSuccessful)
                    {
                        navigateToRegistration()

                    }
                }
            }
        })
    }

    override fun notifier(position: Int, flag: Int) {
        when(flag) {
            1 -> {
                getAllDishes(categoryList!![position].id)

            }

            2 -> {

                intent = Intent(this, OrderDescriptionActivity::class.java)
                intent.putExtra("dishes", Gson().toJson(dishesDetails!![position]))
                startActivity(intent)
            }
        }
    }


}



