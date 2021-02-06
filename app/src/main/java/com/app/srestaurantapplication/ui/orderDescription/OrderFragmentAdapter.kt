package com.app.srestaurantapplication.ui.orderDescription

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.model.BagSharedPrefrences
import com.app.srestaurantapplication.data.model.DishesDataDetails
import com.app.srestaurantapplication.data.model.PriceModel
import com.app.srestaurantapplication.data.model.ProductDetail
import com.app.srestaurantapplication.util.hide
import kotlinx.android.synthetic.main.items_fragment_order.view.*


class OrderFragmentAdapter (private var productDetails: List<ProductDetail>?,private val dishes:DishesDataDetails)
    : RecyclerView.Adapter<OrderFragmentAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.items_fragment_order, parent, false)
        return ViewHolder(
            view
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val OproductDetails: ProductDetail = productDetails!![position]


        if (OproductDetails.size == null && OproductDetails.type == null) {

            holder.itemView.group2_items_fragment_order.hide()
            holder.itemView.group1_items_fragment_order.hide()
        } else if (OproductDetails.size == null) {
            holder.itemView.group1_items_fragment_order.hide()
            holder.itemView.type_items_fragment_order_textView.text = OproductDetails.type!!.name
        } else if (OproductDetails.type == null) {
            holder.itemView.group2_items_fragment_order.hide()
            holder.itemView.size_items_fragment_order_textView.text = OproductDetails.size.name

        } else {
            holder.itemView.size_items_fragment_order_textView.text = OproductDetails.size.name
            holder.itemView.type_items_fragment_order_textView.text = OproductDetails.type.name
        }

        holder.itemView.price_items_fragment_order_textView.text = OproductDetails.price.toString()


           holder.itemView.layout_container_items_fragment_order.setOnClickListener {
            OproductDetails.isSelected=!(OproductDetails.isSelected)
               if (OproductDetails.isSelected)
               {
                   holder.itemView.checkmark_itemsFragmentOrder.visibility=View.VISIBLE
                   val item=PriceModel(OproductDetails.id,OproductDetails.price)
                   BagSharedPrefrences.setListPrice(item)

               }
               else
               {
                   holder.itemView.checkmark_itemsFragmentOrder.visibility=View.GONE
                   BagSharedPrefrences.retreiveListPrice().remove(BagSharedPrefrences.retreiveListPrice().find { it.id == OproductDetails.id })

               }

        }

    }


    override fun getItemCount(): Int {
            return productDetails!!.size

        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    }
