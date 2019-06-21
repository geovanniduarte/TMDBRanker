package com.co.geo.rankertmdb.movielist.data.repository.datasource

import com.co.geo.rankertmdb.movielist.data.mapper.MoviesDataMapper
import com.co.geo.rankertmdb.movielist.data.model.entity.MovieEntity
import com.co.geo.rankertmdb.movielist.data.model.entity.MoviesData
import com.co.geo.rankertmdb.movielist.data.net.MovieService
import io.reactivex.Flowable

class TMDBDatasource(private val movieService: MovieService, private val moviesDataMapper: MoviesDataMapper): MovieDatasource {
    override fun insertOne(movieEntity: MovieEntity): Long =
        0L


    override fun findOne(movieEntity: MovieEntity): Flowable<MovieEntity> =
        Flowable.empty<MovieEntity>()

    override fun getImageConfig(apiKey: String): Flowable<String> =
        movieService.getImageConfig(apiKey)
            .map {
                it.images.baseUrl
            }

    override fun getMovies(apiKey: String, query: String, page: Int, year: Int): Flowable<MoviesData>
            = movieService.getAllMovies(apiKey, query, page, year)
                          .map {
                              moviesDataMapper.transform(it)
                          }



}