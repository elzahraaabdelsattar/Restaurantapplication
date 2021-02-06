package com.app.srestaurantapplication.ui.orderDescription

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.local.PrefManager
import com.app.srestaurantapplication.data.model.*
import com.app.srestaurantapplication.util.CallBack
import com.app.srestaurantapplication.util.hide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_orders.*


class OrdersFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<OrderFragmentAdapter.ViewHolder>? = null
    var type:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       return inflater.inflate(R.layout.fragment_orders, container, false)

    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        val prefManager= PrefManager(requireContext())
        val bundle = arguments
        val dishes: DishesDataDetails? = bundle!!.getParcelable("dishes")

        /////set_Product_Details
        if(dishes!!.price==0.0)
        {
            recycler_view_orderFragment.apply {
                layoutManager = LinearLayoutManager(activity,RecyclerView.HORIZONTAL,true)
                // set the custom adapter to the RecyclerView
                adapter = OrderFragmentAdapter(dishes.productDetails,dishes)
            }

        }
        else{
            group_fragment_orders.hide()

        }

        //spinner
        val count = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)

        spinner_orders_fragment.adapter = ArrayAdapter(
            activity!!.applicationContext,
            R.layout.support_simple_spinner_dropdown_item, count)

        spinner_orders_fragment.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                type= parent?.getItemAtPosition(position).toString()
                //bag!!.count=number
                view!!.textAlignment = View.TEXT_ALIGNMENT_CENTER

            }


        }

        buy_now_orderFragment_button.setOnClickListener {

            if (dishes.price !=0.0)
            {
                val item=BagModel(dishes.name,dishes.image_url.plus(dishes.image),dishes.price,type.toInt(),1,dishes.id)
                BagSharedPrefrences.setBagData(item)
                val snackbar =Snackbar.make(order_fragment_layout,"تمت الاضافه الي الحقيبه",Snackbar.LENGTH_LONG)
                snackbar.setBackgroundTint(R.color.app_main_color)
                snackbar.show()

            }
            else
            {
                val listPrices: MutableList<PriceModel> = BagSharedPrefrences.retreiveListPrice()


                for (i in 0 until listPrices.size)
                {
                    val item=BagModel(dishes.name,dishes.image_url.plus(dishes.image),listPrices[i].price,type.toInt(),0,dishes.productDetails!![i].id)
                    BagSharedPrefrences.setBagData(item)

                }
                val snackbar =Snackbar.make(order_fragment_layout,"تمت الاضافه الي الحقيبه",Snackbar.LENGTH_LONG)
                snackbar.setBackgroundTint(R.color.green_color)
                snackbar.show()
                listPrices.clear()

            }

        }





    }


}

