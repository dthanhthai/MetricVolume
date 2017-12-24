package com.research.thaidt.metricvolumn.ui.activity

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import com.research.thaidt.metricvolumn.R
import com.research.thaidt.metricvolumn.data.repository.BittrexService
import com.research.thaidt.metricvolumn.model.MarketHistory
import com.research.thaidt.metricvolumn.model.MarketHistoryResult
import com.research.thaidt.metricvolumn.ui.adapter.VolBuyAdapter
import com.research.thaidt.metricvolumn.ui.adapter.VolRecentlyAdapter
import com.research.thaidt.metricvolumn.ui.adapter.VolSellAdapter
import com.research.thaidt.metricvolumn.utils.Constants
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
    var cacheVolBuy: MutableList<MarketHistoryResult> = arrayListOf()
    var cacheVolSell: MutableList<MarketHistoryResult> = arrayListOf()
    var retrofit: Retrofit? = null
    var lastTransactionIndex: Long = -1
    var totalVolSell: Double = 0.0
    var totalVolBuy: Double = 0.0
    var totalVolTransaction: Double = 0.0
    var mHandler: Handler? = null
    var mReloadRunable: Runnable? = null
    var menu: Menu? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.title = "USDT-BTC";
        mHandler = Handler()
        resetCacheVol()

        retrofit = Retrofit.Builder()
                .baseUrl(Constants.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        intReloadTask()
    }

    private fun intReloadTask() {
        disposable?.dispose();
        mHandler?.removeCallbacks(mReloadRunable)
        mReloadRunable = Runnable {
            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
            loadHistoryData()
        }
        mHandler?.post(mReloadRunable)
    }

    private fun loadHistoryData() {
        val bittrexService = retrofit?.create(BittrexService::class.java)
        disposable = bittrexService?.getMarketHistory("USDT-BTC")
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                        { responseData: MarketHistory? ->
                            if (responseData != null && responseData?.success!!) {
                                Log.i(TAG, "Result size: " + responseData.result?.size)
                                responseData.result?.let { handleVolBuySell(it) }

                                var buyPercent = Math.round(totalVolBuy * 100 / (totalVolBuy + totalVolSell) * 100.00) / 100.00
                                var sellPercent = Math.round(totalVolSell * 100 / (totalVolBuy + totalVolSell) * 100.00) / 100.00

                                vol_buy_percent_text.text = "(" + buyPercent.toString() + "%)"
                                vol_sell_percent_text.text = "(" + sellPercent.toString() + "%)"
                                if (buyPercent > 50) {
                                    vol_buy_percent_text.setTypeface(null, Typeface.BOLD)
                                } else if (sellPercent > 50) {
                                    vol_sell_percent_text.setTypeface(null, Typeface.BOLD)
                                }

                                vol_buy_total_text.text = (Math.round(totalVolBuy * 100.00) / 100.00).toString()
                                vol_sell_total_text.text = (Math.round(totalVolSell * 100.00) / 100.00).toString()

                                val volRecentlyAdapter = responseData.result?.asReversed()?.let { VolRecentlyAdapter(it) };
                                list_vol_recenlty.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, true)
                                list_vol_recenlty.adapter = volRecentlyAdapter
                                list_vol_recenlty.scrollToPosition(responseData.result?.size!! - 1)

                                val volBuyAdapter = VolBuyAdapter(cacheVolBuy)
                                list_vol_buy.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, true)
                                list_vol_buy.adapter = volBuyAdapter
                                list_vol_buy.scrollToPosition(cacheVolBuy.size - 1)

                                val volSellAdapter = VolSellAdapter(cacheVolSell)
                                list_vol_sell.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, true)
                                list_vol_sell.adapter = volSellAdapter
                                list_vol_sell.scrollToPosition(cacheVolSell.size - 1)


                                //Task done
                                mHandler?.postDelayed(mReloadRunable, 5000)
                            } else {
                                Toast.makeText(this, "Message: " + responseData?.message, Toast.LENGTH_LONG).show()
                                Log.e(TAG, "Error: " + responseData?.message)
                            }
                        },
                        { error ->
                            Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                            Log.e(TAG, "Error: " + error.message)
                        }
                )
    }

    private fun handleVolBuySell(rawDatas: List<MarketHistoryResult>) {
        var reserveList = rawDatas.asReversed()
        for (item in reserveList) {
            if (item.id!! > lastTransactionIndex) {
                lastTransactionIndex = item.id!!
                if (item.orderType?.toLowerCase().equals("buy")) {
                    totalVolBuy += item.total!!
                } else if (item.orderType?.toLowerCase().equals("sell")) {
                    totalVolSell += item.total!!
                }
            }

            if (item.orderType?.toLowerCase().equals("buy")) {
                cacheVolBuy.add(item)
            } else if (item.orderType?.toLowerCase().equals("sell")) {
                cacheVolSell.add(item)
            }
        }
    }

    private fun resetCacheVol() {
        cacheVolBuy = arrayListOf();
        cacheVolSell = arrayListOf()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.getItemId()) {
            R.id.reset_item -> {
                resetCacheVol()
                intReloadTask()
                return true
            }
            R.id.change_market_item -> {

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose();
        mHandler?.removeCallbacks(mReloadRunable)
    }

    override fun onPause() {
        super.onPause()
    }
}
