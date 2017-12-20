package com.research.thaidt.metricvolumn.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by DOAN THANH THAI on 12/20/2017.
 */

class MarketHistory {
    @SerializedName("success")
    @Expose
    var success: Boolean? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("result")
    @Expose
    var result: List<MarketHistoryResult>? = null

}
