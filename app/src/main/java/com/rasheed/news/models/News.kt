package com.rasheed.news.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class News(
    @SerializedName("id")
    var newsId: Int = 0,
    @SerializedName("title")
    var title: String = "",
    @SerializedName("detail")
    var details: String = "",
    @SerializedName("date")
    var date: Date = Date(),
    @SerializedName("image")
    var image: String = "",
    @SerializedName("type")
    var categoryId: Int = 0,
)