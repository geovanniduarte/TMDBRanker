package com.co.geo.rankertmdb.movielist.data.model.response

import com.co.geo.rankertmdb.movielist.data.model.response.Movie
import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page")
    val page:Int,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("results")
    val results: List<Movie>
)