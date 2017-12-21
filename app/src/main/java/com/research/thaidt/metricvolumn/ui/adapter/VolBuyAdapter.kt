package com.research.thaidt.metricvolumn.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.research.thaidt.metricvolumn.R
import com.research.thaidt.metricvolumn.model.MarketHistoryResult
import kotlinx.android.synthetic.main.item_vol_buy.view.*

/**
 * Created by ThaiDT1 on 12/21/2017.
 */
class VolBuyAdapter(private val listItem: List<MarketHistoryResult>) : RecyclerView.Adapter<VolBuyAdapter.ViewHolder>() {

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//       return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_vol_buy, parent, false))
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_vol_buy, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listItem?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return listItem?.size!!
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: MarketHistoryResult) = with(itemView) {
            vol_buy_text.text = data.quantity.toString()
        }
    }
}