package com.co.geo.rankertmdb.movielist.data.model.entity

data class MoviesData(
    val page:Int,
    val totalResults: Int,
    val totalPages: Int,
    val results: List<MovieEntity>
) {
}