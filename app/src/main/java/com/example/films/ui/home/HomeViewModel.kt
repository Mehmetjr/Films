package com.example.films.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.films.models.Film
import com.example.films.models.FilmItem
import com.example.films.network.ApiClient
import com.example.films.network.Repository
import com.example.films.network.Resource
import com.example.films.util.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(val repository: Repository, val ioDispatcher: CoroutineDispatcher) : ViewModel() {



    init {
        getFilmList()
    }
    private val filmList = MutableLiveData<Resource<Film>>()

    fun getFilmListState():LiveData<Resource<Film>> = filmList
    fun getFilmList(){
        viewModelScope.launch(ioDispatcher) {

            filmList.postValue(Resource.progress())
            filmList.postValue(repository.getFilms())

        }
    }
}