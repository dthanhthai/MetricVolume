package com.research.thaidt.metricvolumn.ui.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.research.thaidt.metricvolumn.R
import com.research.thaidt.metricvolumn.model.MarketHistoryResult
import kotlinx.android.synthetic.main.item_vol_buy.view.*
import kotlinx.android.synthetic.main.item_vol_sell.view.*

/**
 * Created by ThaiDT1 on 12/21/2017.
 */
class VolSellAdapter(private var listItem: List<MarketHistoryResult>) : RecyclerView.Adapter<VolSellAdapter.ViewHolder>() {

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//       return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_vol_buy, parent, false))
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_vol_sell, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listItem?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return listItem?.size!!
    }

    fun clearData(){
        listItem = emptyList()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: MarketHistoryResult) = with(itemView) {
            if (data.orderType?.toLowerCase().equals("sell")) {
                if(data.total!! >= 1000){
                    vol_sell_text.setTextColor(Color.parseColor("#760d1f"))
                    vol_sell_text.setTypeface(null, Typeface.BOLD)
                    Log.i("VolBuyAdapter", "[ViewHolder Color] " + data.total)
                }else{
                    vol_sell_text.setTextColor(Color.parseColor("#757575"))
                    vol_sell_text.setTypeface(null, Typeface.NORMAL)
                }
                Log.i("VolBuyAdapter", "[ViewHolder] " + data.total)
                vol_sell_text.text = (Math.round(data.total!! * 100.00) / 100.00).toString()
            }
        }
    }

}