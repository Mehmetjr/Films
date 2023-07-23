package com.example.films.models


import com.google.gson.annotations.SerializedName

data class FilmItem(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("title")
    val title: String?,
    var isFav : Boolean? = false

)