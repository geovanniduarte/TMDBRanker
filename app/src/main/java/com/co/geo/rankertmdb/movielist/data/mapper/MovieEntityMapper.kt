package com.co.geo.rankertmdb.movielist.data.mapper

import android.util.Log
import com.co.geo.rankertmdb.movielist.data.model.entity.MovieEntity
import com.co.geo.rankertmdb.movielist.data.model.response.Movie
import com.co.geo.rankertmdb.movielist.util.Constants

class MovieEntityMapper: Mapper<Movie, MovieEntity> {

    private fun validateImg(imgName: String?) : String {
        imgName?.let {
            return it
        }
        return Constants.DEFAULT_IMAGE
    }

    override fun transform(input: Movie): MovieEntity = MovieEntity(input.id, input.title, validateImg(input.posterPath), validateImg(input.backdropPath), input.overview, 0f)

    override fun transformList(inputList: List<Movie>): List<MovieEntity>
        = inputList.map {
            transform(it)
        }
}