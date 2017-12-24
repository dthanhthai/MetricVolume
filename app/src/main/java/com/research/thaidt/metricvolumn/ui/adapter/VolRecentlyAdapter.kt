package com.research.thaidt.metricvolumn.ui.adapter

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
import kotlinx.android.synthetic.main.item_vol_recently.view.*
import java.text.DecimalFormat

/**
 * Created by ThaiDT1 on 12/21/2017.
 */
class VolRecentlyAdapter(private val listItem: List<MarketHistoryResult>) : RecyclerView.Adapter<VolRecentlyAdapter.ViewHolder>() {

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//       return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_vol_buy, parent, false))
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_vol_recently, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position == 0) {
            holder.bind(listItem?.get(position)!!, listItem?.get(position)!!)
        }else{
            holder.bind(listItem?.get(position)!!, listItem?.get(position - 1)!!)
        }
    }

    override fun getItemCount(): Int {
        return listItem?.size!!
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: MarketHistoryResult, previousItem: MarketHistoryResult?) = with(itemView) {

            recently_price_text.text = (Math.round(data.price!! * 1000.00) / 1000.00).toString()

            if (data.orderType?.toLowerCase().equals("buy")) {
                recently_vol_text.setTextColor(Color.parseColor("#006400"))
            } else if (data.orderType?.toLowerCase().equals("sell")) {
                recently_vol_text.setTextColor(Color.parseColor("#760d1f"))
            }
            if (data.total!! >= 1000) {
                recently_vol_text.setTypeface(null, Typeface.BOLD)
            } else {
                recently_vol_text.setTypeface(null, Typeface.NORMAL)
            }
            recently_vol_text.text = (Math.round(data.total!! * 100.00) / 100.00).toString()

            var trendPercent: Double = 0.0;
            var delta = (data.price!! - previousItem?.price!!)
            trendPercent = Math.round(delta * 100.00 / data.price!! * 1000.00) / 1000.00
            if(delta > 0.0){
                recently_trend_percent_text.setTextColor(Color.parseColor("#006400"))
                recently_trend_percent_text.text = "(+" + trendPercent.toString() + "%)"
//                if(delta >= 0.5){
//                    recently_price_text.setTypeface(null, Typeface.BOLD)
//                }else{
//                    recently_price_text.setTypeface(null, Typeface.NORMAL)
//                }
            }else if(delta < 0.0){
                recently_trend_percent_text.setTextColor(Color.parseColor("#760d1f"))
                recently_trend_percent_text.text = "(" + trendPercent.toString() + "%)"
//                if(delta <= -0.5){
//                    recently_price_text.setTypeface(null, Typeface.BOLD)
//                }else{
//                    recently_price_text.setTypeface(null, Typeface.NORMAL)
//                }
            }else{
                recently_trend_percent_text.visibility = View.INVISIBLE
            }
        }
    }

}