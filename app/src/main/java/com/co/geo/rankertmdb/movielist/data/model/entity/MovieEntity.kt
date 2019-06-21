package com.co.geo.rankertmdb.movielist.data.model.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    @ColumnInfo(name="poster_path")
    val posterPath: String,
    @ColumnInfo(name="backdrop_path")
    val backdropPath: String,
    val overview: String,
    var rank: Float?
): Parcelable {
}