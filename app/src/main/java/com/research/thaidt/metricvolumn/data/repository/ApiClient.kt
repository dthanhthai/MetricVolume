package com.research.thaidt.metricvolumn.data.repository

import com.research.thaidt.metricvolumn.utils.Constants

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by DOAN THANH THAI on 6/17/2017.
 */

object ApiClient {

    private var INSTANCE: Retrofit? = null

    val client: Retrofit?
        @Synchronized get() {
            if (INSTANCE == null) {
                INSTANCE = Retrofit.Builder()
                        .baseUrl(Constants.URL_BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
            }
            return INSTANCE
        }
}
