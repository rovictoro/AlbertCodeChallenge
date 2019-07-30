package com.albert.codechallenge.api

import com.albert.codechallenge.model.Search

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface SearchService {

    @GET("search.json")
    fun getSearch( @Query("q") q: String, @Query("page") page: String ): Observable<Search>

    @GET("search.json")
    fun getTitleSearch( @Query("title") title: String): Observable<Search>

    @GET("search.json")
    fun getAuthorSearch( @Query("author") author: String): Observable<Search>

}
