package com.example.films.models


import com.google.gson.annotations.SerializedName

data class FilmDetail(

    @SerializedName("id")
    val id: Int?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("title")
    val title: String?,
    var isFav : Boolean? = false

)