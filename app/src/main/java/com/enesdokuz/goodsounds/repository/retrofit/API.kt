package com.enesdokuz.goodsounds.repository.retrofit

import com.enesdokuz.goodsounds.model.Category
import com.enesdokuz.goodsounds.model.Sound
import io.reactivex.Observable
import retrofit2.http.GET

interface API {

    @GET("sounds.html")
    fun getSounds(): Observable<List<Sound>>

    @GET("categories.html")
    fun getCategories(): Observable<List<Category>>
}