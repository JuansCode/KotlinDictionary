package com.jpimentel.kotlindic.network

import com.jpimentel.kotlindic.model.UrbanResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RestService {
    @Headers("X-RapidAPI-Key: 69f6a73b2amsh28357b07ce82d07p13143ejsnbac2789d32fa")
    @GET("/define")
    fun getDefinitions(
        @Query("term") term: String?
    ): Single<UrbanResponse>
}