package com.example.latestnews.Api

import com.example.latestnews.model.headlines
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("top-headlines")
    fun getHeadlines(
        @Query("country") country:String,
        @Query("apiKey") apiKey:String
                     ):Call<headlines>

    @GET("everything")
    fun getSpecific(
        @Query("q") query:String,
        @Query("apiKey") apiKey:String
    ):Call<headlines>

}