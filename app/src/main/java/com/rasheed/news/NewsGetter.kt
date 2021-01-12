package com.rasheed.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.rasheed.news.api.Api

import com.rasheed.news.models.News
import com.rasheed.news.models.Type

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsGetter{
var api: Api
    init {

        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.1.105/news/apis/"  )
            .build()
        api = retrofit.create(Api::class.java)
    }
    fun fetchNews(): MutableLiveData<List<News>>{
        return fetchNewsMetaData(api.fetchNews())

    }
    fun fetchNewsByCategory(CategoryId:Int): MutableLiveData<List<News>>{
        return fetchNewsMetaData(api.fetchNewsByCategory(CategoryId))

    }
    fun fetchNewsMetaData( newsHomePageRequest:Call<List<News>>):
            MutableLiveData<List<News>> {
        val responseLiveData: MutableLiveData<List<News>> = MutableLiveData()

             newsHomePageRequest.enqueue(object : Callback<List<News>> {
             override fun onResponse(call: Call<List<News>>, response: Response<List<News>>) {
                var newsResponse = response.body()
                var newsList = newsResponse
                    ?: mutableListOf()
                responseLiveData.value = newsList


            }

            override fun onFailure(call: Call<List<News>>, t: Throwable) {

            }
        })
        return responseLiveData
    }

    fun fetchNewsType():MutableLiveData<List<Type>>{
        val responseLiveData: MutableLiveData<List<Type>> = MutableLiveData()
        val TypeHomePageRequest: Call<List<Type>> = api.fetchNewsType()

        TypeHomePageRequest.enqueue(object : Callback<List<Type>> {
            override fun onResponse(call: Call<List<Type>>, response: Response<List<Type>>) {
                var typeList = response.body()
                    ?: mutableListOf()
                responseLiveData.value = typeList

            }
            override fun onFailure(call: Call<List<Type>>, t: Throwable) {

            }
        })
       return responseLiveData
    }
}