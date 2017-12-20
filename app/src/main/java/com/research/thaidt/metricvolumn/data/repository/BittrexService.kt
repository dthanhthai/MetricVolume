package com.research.thaidt.metricvolumn.data.repository

import com.research.thaidt.metricvolumn.model.MarketHistoryResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by DOAN THANH THAI on 12/20/2017.
 */
interface BittrexService {
    @GET("public/getmarkethistory")
    fun getMarketHistory(@Query("market") marketName: String): Observable<MarketHistoryResult>
}