package com.app.srestaurantapplication.ui.foodMenu

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.model.CategoriesList
import com.app.srestaurantapplication.util.CallBack
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_food_menu.view.*
import kotlinx.android.synthetic.main.items_main_menu.view.*

class FoodMenuAdapter(private var categoriesList: MutableList<CategoriesList>?, var context:Context, var callBack: CallBack)
    :RecyclerView.Adapter<FoodMenuAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FoodMenuAdapter.ViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_main_menu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val category:CategoriesList= categoriesList!![position]


        holder.itemView.title_main_menu_item_textView.text=category.name
        holder.itemView.title_main_menu_item_textView.setOnClickListener{

           holder.itemView.title_main_menu_item_textView.setBackgroundResource(R.drawable.off_red_color_rectangle);

            callBack.notifier(position,1)
        }




    }

    override fun getItemCount(): Int
    {
        return categoriesList!!.size

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}

