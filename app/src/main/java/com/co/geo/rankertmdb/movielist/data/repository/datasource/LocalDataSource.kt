package com.co.geo.rankertmdb.movielist.data.repository.datasource
import com.co.geo.rankertmdb.movielist.data.dao.MoviesDatabase
import com.co.geo.rankertmdb.movielist.data.model.entity.MovieEntity
import com.co.geo.rankertmdb.movielist.data.model.entity.MoviesData
import io.reactivex.Flowable

class LocalDataSource(val moviesDatabase: MoviesDatabase): MovieDatasource {

    override fun getMovies(apiKey: String, query: String, page: Int, year: Int): Flowable<MoviesData> =
        Flowable.empty()

    override fun getImageConfig(apiKey: String): Flowable<String> =
        Flowable.empty()

    override fun insertOne(movieEntity: MovieEntity): Long =
            moviesDatabase.getMovieDao().insertOne(movieEntity)


    override fun findOne(movieEntity: MovieEntity): Flowable<MovieEntity> =
            moviesDatabase.getMovieDao().findOne(movieEntity.id).toFlowable()
}