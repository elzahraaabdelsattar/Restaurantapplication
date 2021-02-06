package com.app.srestaurantapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.get
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.local.PrefManager
import com.app.srestaurantapplication.data.local.TOKEN
import com.app.srestaurantapplication.data.model.CategoriesList
import com.app.srestaurantapplication.data.model.CityModel
import com.app.srestaurantapplication.data.model.ResponseDto
import com.app.srestaurantapplication.data.remote.RetrofitClient
import com.app.srestaurantapplication.ui.check.CheckActivity
import kotlinx.android.synthetic.main.activity_location.*
import kotlinx.android.synthetic.main.fragment_orders.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationActivity : AppCompatActivity() {

    val list = mutableListOf<String>()
    lateinit var city:String
     var cityId:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        setLocation()
        continue_bagActivity_button.setOnClickListener {

            val intent = Intent(this,CheckActivity::class.java)
            intent.putExtra("flag","delivery")
            startActivity(intent)
        }


    }

    private fun setLocation() {

        val prefManager= PrefManager(this)
        val token="Bearer".plus(prefManager.retrieveString(TOKEN))

        RetrofitClient.instance.getCities(token).enqueue(object:Callback<ResponseDto<MutableList<CityModel>?>?>{
            override fun onFailure(
                call: Call<ResponseDto<MutableList<CityModel>?>?>,
                t: Throwable
            ) {
                Toast.makeText(this@LocationActivity,t.message.toString(),Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<ResponseDto<MutableList<CityModel>?>?>,
                response: Response<ResponseDto<MutableList<CityModel>?>?>
            ) {
                if (response.code()==200)
                {
                    if (response.isSuccessful)
                        {
                            list.add("اختر مدينتك")
                        for (i in 1 until response.body()!!.data!!.size)
                        {
                            list.add(response.body()!!.data!![i].name)
                        }

                        spinner_locationActivity.adapter = ArrayAdapter(
                            this@LocationActivity,
                            R.layout.support_simple_spinner_dropdown_item, list)

                        spinner_locationActivity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                            }

                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {

                                city= parent?.getItemAtPosition(position).toString()
                                cityId=parent?.getItemIdAtPosition(position)!!.toString()
                                prefManager.saveString("city_id",cityId)

                            }



                        }

                    }
                }
                else
                {
                    Toast.makeText(this@LocationActivity,"Error In Data",Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}