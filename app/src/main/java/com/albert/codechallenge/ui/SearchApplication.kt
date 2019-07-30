package com.albert.codechallenge.ui

import android.app.Application
import com.albert.codechallenge.dagger.*
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso




class SearchApplication : Application() {

    companion object{
       const val BASE_URL = "http://openlibrary.org/" //search.json?q=the+lord+of+the+rings"
       fun COVER_URL_LARGE(s:String):String =  "http://covers.openlibrary.org/b/id/$s-L.jpg"
       fun COVER_URL_MIDDLE(s:String):String =  "http://covers.openlibrary.org/b/id/$s-M.jpg"
    }

    lateinit var _activityComponent: ActivityComponent

    override fun onCreate() {
        super.onCreate()
        _activityComponent = createComponent()

        /*val builder = Picasso.Builder(this)
        builder.downloader( OkHttp3Downloader(this/*, Integer.MAX_VALUE*/))
        val built = builder.build()
        built.setIndicatorsEnabled(true)
        built.isLoggingEnabled = true
        Picasso.setSingletonInstance(built)*/

    }

    fun createComponent(): ActivityComponent {
        return DaggerActivityComponent.builder()
                .appModule(AppModule(this))
                .netModule(NetModule(BASE_URL))
                .apiModule(ApiModule())
                .build()
    }

    fun getActivityComponent(): ActivityComponent {
        return _activityComponent
    }




}
