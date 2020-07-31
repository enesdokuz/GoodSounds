package com.enesdokuz.goodsounds.repository.retrofit

import com.enesdokuz.goodsounds.model.Category
import com.enesdokuz.goodsounds.model.Sound
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Service {

    private fun httpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor())

        return builder.build()
    }

    private val api = Retrofit.Builder()
        .baseUrl("http://enesdokuz.com/api/goodsounds/")
        .client(httpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(API::class.java)

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    fun getSounds(): Observable<List<Sound>> {
        return api.getSounds()
    }

    fun getCategories(): Observable<List<Category>> {
        return api.getCategories()
    }

}