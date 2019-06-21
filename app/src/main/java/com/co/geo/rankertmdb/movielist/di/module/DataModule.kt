package com.co.geo.rankertmdb.movielist.di.module

import android.arch.persistence.room.Room
import android.content.Context
import com.co.geo.rankertmdb.movielist.data.dao.MoviesDatabase
import com.co.geo.rankertmdb.movielist.data.mapper.MovieEntityMapper
import com.co.geo.rankertmdb.movielist.data.mapper.MoviesDataMapper
import com.co.geo.rankertmdb.movielist.data.net.MovieService
import com.co.geo.rankertmdb.movielist.data.repository.MovieRepository
import com.co.geo.rankertmdb.movielist.data.repository.datasource.LocalDataSource
import com.co.geo.rankertmdb.movielist.data.repository.datasource.TMDBDatasource
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun providesMovieEntityMapper() = MovieEntityMapper()

    @Provides
    fun providesMovieDataMapper(movieEntityMapper: MovieEntityMapper) = MoviesDataMapper(movieEntityMapper)

    @Provides
    fun providesTMDBDatasource(movieService: MovieService, moviesDataMapper: MoviesDataMapper) = TMDBDatasource(movieService, moviesDataMapper)

    @Provides
    fun providesMovieRepository(tmdbDatasource: TMDBDatasource, localDataSource: LocalDataSource) = MovieRepository(tmdbDatasource, localDataSource)

    @Provides
    fun provideDatabase(context: Context): MoviesDatabase =
        Room.databaseBuilder(context, MoviesDatabase::class.java, "movies.db").build()

    @Provides
    fun providesLocalDataSource(moviesDatabase: MoviesDatabase) = LocalDataSource(moviesDatabase)
}