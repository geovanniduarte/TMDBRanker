package com.co.geo.rankertmdb.movielist.di.component

import com.co.geo.rankertmdb.movielist.di.module.ApplicationModule
import com.co.geo.rankertmdb.movielist.presentation.MoviesActivity
import com.co.geo.rankertmdb.movielist.di.module.DataModule
import com.co.geo.rankertmdb.movielist.di.module.NetModule
import com.co.geo.rankertmdb.movielist.presentation.MovieFragment
import com.co.geo.rankertmdb.movielist.presentation.MoviePagerFragment
import com.co.geo.rankertmdb.movielist.util.mvvm.ViewModelModule
import dagger.Component

@Component(modules = [
    DataModule::class,
    NetModule::class,
    ApplicationModule::class,
    ViewModelModule::class
])
interface AppComponent {
    fun inject(moviesActivity: MoviesActivity) //inject with @Inject in moviesactivity
    fun inject(moviePagerFragment: MoviePagerFragment)
    fun inject(moviePagerFragment: MovieFragment)
}