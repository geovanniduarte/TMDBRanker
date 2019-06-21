package com.co.geo.rankertmdb.movielist.data.model.response

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName( "id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName( "backdrop_path")
    val backdropPath: String?,
    @SerializedName( "overview")
    val overview: String
) {

}