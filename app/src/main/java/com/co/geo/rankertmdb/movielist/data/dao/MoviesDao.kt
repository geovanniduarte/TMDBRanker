package com.co.geo.rankertmdb.movielist.data.dao

import android.arch.persistence.room.*
import com.co.geo.rankertmdb.movielist.data.model.entity.MovieEntity
import io.reactivex.Maybe


@Dao
abstract class MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOne(movieEntity: MovieEntity): Long

    @Query("SELECT * FROM movies")
    abstract fun getAllMovies(): Maybe<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id = :id")
    abstract fun findOne(id: Int): Maybe<MovieEntity>


}