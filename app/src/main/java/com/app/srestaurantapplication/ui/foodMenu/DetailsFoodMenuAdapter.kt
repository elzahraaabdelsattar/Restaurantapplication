package com.app.srestaurantapplication.ui.foodMenu

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.model.BagModel
import com.app.srestaurantapplication.data.model.BagSharedPrefrences
import com.app.srestaurantapplication.data.model.DishesDataDetails
import com.app.srestaurantapplication.util.CallBack
import com.app.srestaurantapplication.util.hide
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.items_details_food_menu.view.*

class DetailsFoodMenuAdapter (private var dishesDetailsList: List<DishesDataDetails>?, var context: Context,var callBack: CallBack)
    : RecyclerView.Adapter<DetailsFoodMenuAdapter.ViewHolder>()

{
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_details_food_menu, parent, false)
        return ViewHolder(
            view
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dishesDetails:DishesDataDetails=dishesDetailsList!![position]

        holder.itemView.title_items_details_food_menu_textView.text=dishesDetails.name
       if (dishesDetails.price != 0.0)
       {
           holder.itemView.price_items_details_food_menu.text=dishesDetails.price.toString()
       }
        else
       {
          // holder.itemView.price_items_details_food_menu.text=dishesDetails.productDetails!![0].price.toString()
           holder.itemView.price_items_details_food_menu.hide()
       }

        val image=dishesDetails.image_url.plus(dishesDetails.image)

        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(context)
            .load(image)
            .apply(requestOptions)
            .into(holder.itemView.image_items_details_food_menu_imageView)
             holder.itemView.container_itemsMainMenu.setOnClickListener{callBack.notifier( position,2)}



    }

    override fun getItemCount(): Int {
        return dishesDetailsList!!.size

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}