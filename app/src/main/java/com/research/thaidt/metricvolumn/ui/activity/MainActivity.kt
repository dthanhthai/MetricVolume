package com.research.thaidt.metricvolumn.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.research.thaidt.metricvolumn.R
import com.research.thaidt.metricvolumn.data.repository.ApiClient
import com.research.thaidt.metricvolumn.data.repository.BittrexService
import com.research.thaidt.metricvolumn.model.MarketHistoryResult
import com.research.thaidt.metricvolumn.utils.Constants
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import io.reactivex.android.schedulers.AndroidSchedulers

class MainActivity : AppCompatActivity() {

    var disposable: Disposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        val bittrexService = retrofit.create(BittrexService::class.java)

        disposable = bittrexService.getMarketHistory("USDT-BTC").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                { },{}
        )
    }
}
