package com.research.thaidt.metricvolumn.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import com.research.thaidt.metricvolumn.R
import com.research.thaidt.metricvolumn.data.repository.ApiClient
import com.research.thaidt.metricvolumn.data.repository.BittrexService
import com.research.thaidt.metricvolumn.model.MarketHistory
import com.research.thaidt.metricvolumn.model.MarketHistoryResult
import com.research.thaidt.metricvolumn.ui.adapter.VolBuyAdapter
import com.research.thaidt.metricvolumn.utils.Constants
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity";

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
                { result: MarketHistory? ->
                    if (result != null && result?.success!!) {
                        Log.i(TAG, "Result size: " + result.result?.size)
                        val volBuyAdapter = result.result?.let { VolBuyAdapter(it) }
                        list_vold_buy.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
                        list_vold_buy.adapter = volBuyAdapter
                    }
                },
                { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                    Log.i(TAG, "Error: " + error.message)
                }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose();
    }

    override fun onPause() {
        super.onPause()
    }
}
