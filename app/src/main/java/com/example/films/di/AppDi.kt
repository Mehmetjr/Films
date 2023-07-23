package com.example.films.di

import com.example.films.network.ApiService
import com.example.films.network.Repository
import com.example.films.ui.detail.DetailViewModel
import com.example.films.ui.home.HomeViewModel
import com.example.films.util.Constants
import com.example.films.util.PreferenceHelper
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {

        OkHttpClient.Builder().apply {

            addInterceptor {
                val request =
                    it.request().newBuilder().addHeader("Authorization", Constants.BEARER_TOKEN)
                        .build()
                it.proceed(request)
            }

            addInterceptor(HttpLoggingInterceptor().apply {

                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
        }.build()


    }
    single {
        Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(get()).build()
            .create(ApiService::class.java)
    }
   /* single {
        provideRetrofitAPI(get())
    }*/
}

val repositoryModule = module {
    factory<Repository> {
        Repository(get())
    }

    single(createdAtStart = true) {
        PreferenceHelper(androidContext())
    }
}

val viewModelModules= module{
    viewModel{
        HomeViewModel(get(), get(named("IO")))
    }

    viewModel{
        DetailViewModel(get(), get(named("IO")),get())
    }
}

val dispatchersModule = module{
    single(named("IO")){
        Dispatchers.IO
    }

    single(named("Main")){
        Dispatchers.Main
    }
    single(named("Default")){
        Dispatchers.Default
    }
}

val appModules= listOf(repositoryModule, dispatchersModule, viewModelModules, networkModule)


//private fun provideRetrofitAPI(retrofit: Retrofit) = retrofit.create(ApiService::class.java)
