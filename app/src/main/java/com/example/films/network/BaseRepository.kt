package com.example.films.network

import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException
import java.net.UnknownServiceException

open class BaseRepository {
    suspend fun <T> callNetworkRequest(requestBlock: suspend () -> Response<T>): Resource<T> {
        return try {
            val response = requestBlock.invoke()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Resource.success(body)
            } else {
                Resource.error(Error(response.code(), response.message()))
            }
        } catch (e: Exception) {

            val networkError = when (e) {
                is ConnectException, is UnknownHostException, is UnknownServiceException -> Error(
                    errorMessage = "İnternet bağlantınızı kontrol edin"
                )
                else -> Error(errorMessage = e.message.orEmpty())

            }
            Resource.error(networkError)
        }

    }
}