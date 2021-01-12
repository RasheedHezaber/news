package com.rasheed.news.models

import com.google.gson.annotations.SerializedName

data class Type (
    @SerializedName("id")
    var typeId: Int = 0,
    @SerializedName("type")
   var typeName: String=""
)