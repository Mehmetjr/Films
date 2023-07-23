package com.example.films.network

import com.example.films.models.Film
import com.example.films.models.FilmDetail

class Repository(val service: ApiService) : BaseRepository() {
    suspend fun getFilms(): Resource<Film> {

        return callNetworkRequest {
            service.getMovieList()
        }
    }

    suspend fun filmDetail(id : Int ) : Resource<FilmDetail>{
        return callNetworkRequest {
            service.getFilmDetail(id)
        }
    }


}