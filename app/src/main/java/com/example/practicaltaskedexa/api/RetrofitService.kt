package com.example.practicaltaskedexa.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    var retrofitService: RetrofitService? = null

    fun getInstance(): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://api.npoint.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}