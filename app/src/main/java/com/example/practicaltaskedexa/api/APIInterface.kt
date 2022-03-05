package com.example.practicaltaskedexa.api

import retrofit2.Call
import com.example.practicaltaskedexa.roomdatabase.entities.EmployeeEntity
import retrofit2.Response
import retrofit2.http.*

interface APIInterface {

    @GET("368124fffd7ae70f3b57")
    fun getEmployee(): Call<ArrayList<EmployeeEntity>>

}