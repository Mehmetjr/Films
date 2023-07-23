package com.example.films.models


import com.google.gson.annotations.SerializedName

data class Film(
    @SerializedName("results")
    val filmItems: MutableList<FilmItem>
)