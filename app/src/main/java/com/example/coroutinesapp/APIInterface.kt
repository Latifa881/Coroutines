package com.example.coroutinesapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface APIInterface {
    @Headers("Content-Type: application/json")
    @GET("advice")
    fun doGetListResources(): Call<Advices>?
}