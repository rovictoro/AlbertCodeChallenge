package com.albert.codechallenge.dagger

import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient

object OkHttpProvider {
    private lateinit var instance: OkHttpClient

    val okHttpInstance: OkHttpClient
        get() {
            //if (instance == null) {

                instance = OkHttpClient.Builder()
                        .readTimeout(5, TimeUnit.MINUTES)
                        .connectTimeout(5, TimeUnit.MINUTES)
                        .callTimeout(5, TimeUnit.MINUTES)
                        .addInterceptor { chain ->
                            val request = chain.request()
                            chain.proceed(request)
                        }
                        .retryOnConnectionFailure(true)
                        .build()
            //}
            return instance

        }
}
