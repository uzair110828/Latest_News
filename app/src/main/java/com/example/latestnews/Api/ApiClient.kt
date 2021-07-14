package com.example.latestnews.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class ApiClient private  constructor(){
    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"
        private var INSTANCE: ApiClient? = null

        fun getInstance(): ApiClient? {
            if (INSTANCE == null) {
                INSTANCE = ApiClient()
            }
            return INSTANCE
        }

        fun create():ApiInterface{
            val  mRetrofit = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
          return mRetrofit.create(ApiInterface::class.java)

        }
    }
}
