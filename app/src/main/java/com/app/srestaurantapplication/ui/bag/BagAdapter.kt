package com.app.srestaurantapplication.ui.bag

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.model.BagModel
import com.app.srestaurantapplication.data.model.ItemsWithMainPrice
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.items_bag_details.view.*
import kotlinx.android.synthetic.main.items_details_food_menu.view.*

class BagAdapter (private var bagList: MutableList<BagModel>,val context:Context,var flag:Int)
    : RecyclerView.Adapter<BagAdapter.ViewHolder>()


{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_bag_details, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: BagAdapter.ViewHolder, position: Int) {
        val OBag:BagModel =bagList[position]
        //var count=OBag.counter

        if (flag==1){
            holder.itemView.group_itemsBagDetails.visibility=View.GONE
            holder.itemView.button_itemsBagDetails.visibility=View.GONE

        }

        holder.itemView.dish_name_itemsBagdetails_textView.text=OBag.name
        holder.itemView.count_dishes_itemsBagdatails.text=OBag.counter.toString()

        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(context)
            .load(OBag.image)
            .apply(requestOptions)
            .into(holder.itemView.dish_image_itemsBagDetails)
        holder.itemView.price_itemsBagDetails_textView.text=OBag.price.toString()

        holder.itemView.plus_itemsBag_imageView.setOnClickListener {
            OBag.counter=OBag.counter+1
            holder.itemView.count_dishes_itemsBagdatails.text=OBag.counter.toString()

        }
        holder.itemView.minus_itemsBag_imageView.setOnClickListener {
            if(OBag.counter==0)
            {
                holder.itemView.minus_itemsBag_imageView.isEnabled=false
            }
            else{
                OBag.counter=OBag.counter-1
              //  Log.d("trtr",OBag.counter.toString())
                holder.itemView.count_dishes_itemsBagdatails.text=OBag.counter.toString()
            }


        }

    }

    override fun getItemCount(): Int {
        return bagList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}



