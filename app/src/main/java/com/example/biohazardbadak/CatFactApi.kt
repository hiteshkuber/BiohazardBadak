package com.example.biohazardbadak

import retrofit2.Call
import retrofit2.http.GET

interface CatFactApi {

    @GET("fact")
    fun getPosts(): Call<Fact>
}