package com.co.geo.rankertmdb.movielist.data.net

import com.co.geo.rankertmdb.movielist.data.model.response.ConfigResponse
import com.co.geo.rankertmdb.movielist.data.model.response.MoviesResponse
import com.co.geo.rankertmdb.movielist.data.model.response.RateResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MovieService {

    @GET("search/movie")
    fun getAllMovies(@Query("api_key") apiKey: String,
                     @Query("query") query: String,
                     @Query("page") page: Int,
                     @Query("year") year: Int) : Flowable<MoviesResponse>

    @GET("configuration")
    fun getImageConfig(@Query("api_key") apiKey: String) : Flowable<ConfigResponse>

    @POST("movie/{movie_id}/rating")
    fun rateMovie() : Flowable<RateResponse>

}