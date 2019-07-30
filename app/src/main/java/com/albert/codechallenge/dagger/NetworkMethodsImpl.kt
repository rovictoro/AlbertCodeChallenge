package com.albert.codechallenge.dagger

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

class NetworkMethodsImpl /*implements NetworkMethods*/(application: Application) {

    private var isNetworkReachable: Boolean = false
    private val manager: ConnectivityManager?

    //@Override
    val isNetworkAvailable: Boolean
        get() {
            isNetworkReachable = false
            if (manager == null) return isNetworkReachable
            val networkInfo = manager.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected) {
                isNetworkReachable = true
            }
            return isNetworkReachable
        }

    init {
        manager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

}
