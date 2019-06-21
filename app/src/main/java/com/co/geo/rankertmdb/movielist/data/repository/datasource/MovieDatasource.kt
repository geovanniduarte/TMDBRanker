package com.co.geo.rankertmdb.movielist.data.repository.datasource

import com.co.geo.rankertmdb.movielist.data.model.entity.MovieEntity
import com.co.geo.rankertmdb.movielist.data.model.entity.MoviesData
import io.reactivex.Flowable

interface MovieDatasource {
    fun getMovies(apiKey: String, query: String, page: Int, year: Int) : Flowable<MoviesData>
    fun getImageConfig(apiKey: String): Flowable<String>
    fun insertOne(movieEntity: MovieEntity) : Long
    fun findOne(movieEntity: MovieEntity) : Flowable<MovieEntity>
}