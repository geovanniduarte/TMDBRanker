package com.co.geo.rankertmdb.movielist.presentation

import android.arch.lifecycle.MutableLiveData
import com.co.geo.rankertmdb.movielist.data.model.entity.MovieEntity
import com.co.geo.rankertmdb.movielist.data.repository.MovieRepository
import com.co.geo.rankertmdb.movielist.util.Constants
import com.co.geo.rankertmdb.movielist.util.mvvm.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList
import javax.inject.Inject

private const val TAG = "MovieListViewModel"
class MovieListViewModel @Inject constructor(private val movieRepository: MovieRepository) : BaseViewModel() {

    val movieListState: MutableLiveData<List<MovieEntity>> = MutableLiveData()
    val isLoadingState: MutableLiveData<Boolean> = MutableLiveData()
    val movieState: MutableLiveData<MovieEntity> = MutableLiveData()
    val changeState: MutableLiveData<Boolean> = MutableLiveData()


    var totalResults: Int = 0

    var moviesListBefore: List<MovieEntity>? = null
    var moviesList : ArrayList<List<MovieEntity>> = ArrayList<List<MovieEntity>>()

    var selectedYear: Int = 2010

    var imgConfig: String? = null

    var page: Int = 1
    set(value) {
        if (value != page) {
            field = value
            getAllMovies(selectedYear)
        }
    }

    var lastYear = selectedYear


    fun getImgConfig()  = movieRepository.getImageConfig("5978a13a40135c5e1ae49c183ed87960")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe {
            isLoadingState.postValue(true)
        }
        .doOnTerminate {
            isLoadingState.postValue(false)
        }
        .subscribe {
            imgConfig = "$it/w780"
            getAllMovies(selectedYear)
        }


    fun getAllMovies(year: Int) = movieRepository
                        .getAllMovies("5978a13a40135c5e1ae49c183ed87960", Constants.QUERY_ALL, page, year)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .doOnSubscribe {
            isLoadingState.postValue(true)
        }
        .doOnTerminate {
            isLoadingState.postValue(false)
        }
        .subscribeBy (
            onNext = {
                totalResults = it.totalResults
                page = it.page
                if (lastYear != selectedYear) {
                    moviesList.clear()
                }
                moviesList.add(page - 1, it.results)

                if(page == 1) {
                    movieListState.value = it.results
                }

                if (lastYear != selectedYear) {
                    changeState.postValue(true)
                }

                lastYear = selectedYear
            },
            onError = {
                it.printStackTrace()
            },
            onComplete = {

            }
        ).addTo(compositeDisposable)


    fun insertOne(movieEntity: MovieEntity) = movieRepository.inserOne(movieEntity)

    fun findOne(movieEntity: MovieEntity) = movieRepository.findOne(movieEntity)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            movieState.value = it
        }
}