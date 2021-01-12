package com.rasheed.news.api

import com.rasheed.news.models.News
import com.rasheed.news.models.Type
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("news_api.php")
    fun fetchNews(): Call<List<News>>
    @GET("news_api.php?")
    fun fetchNewsByCategory(@Query("type") query: Int): Call<List<News>>

    @GET("type_api.php")
    fun fetchNewsType(): Call<List<Type>>

}