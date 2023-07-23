package com.example.films.network

data class Resource<T>(
    var status: Status,
    var data: T? = null,
    var error: Error? = null
) {

    val isStatusSuccess: Boolean = status == Status.SUCCESSFUL
    val isStatusLoading: Boolean = status == Status.LOADING
    val isStatusError: Boolean = status == Status.ERROR

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(status = Status.SUCCESSFUL, data = data)
        }

        fun <T> progress(): Resource<T> {
            return Resource(status = Status.LOADING)
        }

        fun <T> error(error: Error?): Resource<T> {
            return Resource(status = Status.ERROR, error = error)
        }
    }
}

enum class Status { SUCCESSFUL, ERROR, LOADING }

data class Error(
    val errorCode: Int? = null,
    val errorMessage: String = ""
)