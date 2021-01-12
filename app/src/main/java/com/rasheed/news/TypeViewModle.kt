package com.rasheed.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rasheed.news.models.Type

class TypeViewModle: ViewModel() {


    val typeLiveData: LiveData<List<Type>>
    init {
        typeLiveData = NewsGetter().fetchNewsType()
    }
}