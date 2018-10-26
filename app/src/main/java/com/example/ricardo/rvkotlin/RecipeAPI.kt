package com.example.ricardo.rvkotlin

import io.reactivex.Observable
import retrofit2.http.GET

interface RecipeAPI {

    @GET("api/?i=onions,garlic&q=omelet&p=3")
    fun getData() : Observable<JSONResponse>
}
