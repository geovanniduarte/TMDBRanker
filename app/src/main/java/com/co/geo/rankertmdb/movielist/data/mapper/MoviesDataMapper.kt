package com.co.geo.rankertmdb.movielist.data.mapper

import com.co.geo.rankertmdb.movielist.data.model.entity.MoviesData
import com.co.geo.rankertmdb.movielist.data.model.response.MoviesResponse

class MoviesDataMapper(private val movieEntityMapper: MovieEntityMapper) : Mapper<MoviesResponse, MoviesData> {
    override fun transform(input: MoviesResponse): MoviesData {
        val list = movieEntityMapper.transformList(input.results)
       return MoviesData(input.page, input.totalResults, input.totalPages, list)
    }

    override fun transformList(inputList: List<MoviesResponse>): List<MoviesData> = inputList.map {
        transform(it)
    }
}