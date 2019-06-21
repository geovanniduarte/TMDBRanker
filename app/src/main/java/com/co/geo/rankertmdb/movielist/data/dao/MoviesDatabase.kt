package com.co.geo.rankertmdb.movielist.data.dao


import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.co.geo.rankertmdb.movielist.data.model.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun getMovieDao() : MoviesDao
}