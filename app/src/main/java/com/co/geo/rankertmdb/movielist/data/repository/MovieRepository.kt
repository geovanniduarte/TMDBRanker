package com.co.geo.rankertmdb.movielist.data.repository

import com.co.geo.rankertmdb.movielist.data.model.entity.MovieEntity
import com.co.geo.rankertmdb.movielist.data.repository.datasource.LocalDataSource
import com.co.geo.rankertmdb.movielist.data.repository.datasource.TMDBDatasource

class MovieRepository(private val tmdbDatasource: TMDBDatasource, private val localDataSource: LocalDataSource) {
    fun getAllMovies(apiKey: String, query: String, page: Int, year: Int) = tmdbDatasource.getMovies(apiKey, query, page, year)
    fun getImageConfig(apiKey: String) = tmdbDatasource.getImageConfig(apiKey)

    fun findOne(movieEntity: MovieEntity) = localDataSource.findOne(movieEntity)
    fun inserOne(movieEntity: MovieEntity) = localDataSource.insertOne(movieEntity)
}