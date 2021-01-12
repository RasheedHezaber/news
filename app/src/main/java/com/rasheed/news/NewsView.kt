package com.rasheed.news
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rasheed.news.models.News

class NewsView : ViewModel() {
    val newsRepositry = NewsGetter()

    var newsLiveData: LiveData<List<News>>

    init {

       if(MainActivity.categoryId ==0)
        newsLiveData = newsRepositry.fetchNews()
        else
           newsLiveData = newsRepositry.fetchNewsByCategory(MainActivity.categoryId)
    }
}