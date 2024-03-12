package com.example.composetutorial
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("warnings/{id}")
    fun getPostById(@Path("id") postId: Int): Call<WarningResponse>
}