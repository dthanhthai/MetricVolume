package com.research.thaidt.metricvolumn.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by DOAN THANH THAI on 12/20/2017.
 */

class MarketHistoryResult {
    @SerializedName("Id")
    @Expose
    var id: Long? = null
    @SerializedName("TimeStamp")
    @Expose
    var timeStamp: String? = null
    @SerializedName("Quantity")
    @Expose
    var quantity: Double? = null
    @SerializedName("Price")
    @Expose
    var price: Long? = null
    @SerializedName("Total")
    @Expose
    var total: Double? = null
    @SerializedName("FillType")
    @Expose
    var fillType: String? = null
    @SerializedName("OrderType")
    @Expose
    var orderType: String? = null
}
