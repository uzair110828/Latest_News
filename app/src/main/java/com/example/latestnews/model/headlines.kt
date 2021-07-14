package com.example.latestnews.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class headlines(
    @SerializedName("status")
    @Expose
    val status:String,

    @SerializedName("totalResults")
    @Expose
    val totalResults:String,

    @SerializedName("articles")
    @Expose
    val articles:List<Articles>  ) {
}