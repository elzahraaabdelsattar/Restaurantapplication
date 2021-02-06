package com.app.srestaurantapplication.ui.myOrders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.model.BagModel
import com.app.srestaurantapplication.data.model.ResponseMyOrders
import kotlinx.android.synthetic.main.item_my_orders.view.*
import kotlinx.android.synthetic.main.items_bag_details.view.*


class MyOrdersAdapter (private var myOrdersList: MutableList<ResponseMyOrders>?)
    : RecyclerView.Adapter<MyOrdersAdapter.ViewHolder>()


{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOrdersAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_orders, parent, false)
        return MyOrdersAdapter.ViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: MyOrdersAdapter.ViewHolder, position: Int) {
        val OMyOrders: ResponseMyOrders =myOrdersList!![position]

        holder.itemView.serial_number_itemMyOrders_textView.text=OMyOrders.id
        holder.itemView.total_order_price_itemMyOrders_textView.text=OMyOrders.totalPrice
        holder.itemView.order_date_itemMyOrders_textView.text=OMyOrders.createdAt
    }

    override fun getItemCount(): Int {
        return myOrdersList!!.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}