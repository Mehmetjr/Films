package com.example.films.ui.detail


import androidx.lifecycle.*
import com.example.films.models.Film
import com.example.films.models.FilmDetail
import com.example.films.network.ApiClient
import com.example.films.network.Repository
import com.example.films.network.Resource
import com.example.films.util.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class DetailViewModel(
    val repository: Repository,
    val ioDispatcher: CoroutineDispatcher,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val filmDetail = MutableLiveData<Resource<FilmDetail>>()
    fun getFilmDetailState(): LiveData<Resource<FilmDetail>> = filmDetail

    init {

        savedStateHandle.get<Int>("filmid")?.let { getFilmDetail(it) }
    }


    fun getFilmDetail(filmId: Int) {
        viewModelScope.launch(ioDispatcher) {

            filmDetail.postValue(Resource.progress())
            filmDetail.postValue(repository.filmDetail(id = filmId))
        }


    }

}