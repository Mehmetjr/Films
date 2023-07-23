package com.example.films.network

import com.example.films.models.Film
import com.example.films.models.FilmDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {

    @GET("popular")
    suspend fun getMovieList(): Response<Film>

    @GET("{movieId}")
    suspend fun getFilmDetail(@Path("movieId") movieId: Int): Response<FilmDetail>

}