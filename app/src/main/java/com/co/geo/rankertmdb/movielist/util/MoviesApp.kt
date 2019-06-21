package com.co.geo.rankertmdb.movielist.util

import android.app.Application
import com.co.geo.rankertmdb.movielist.di.component.AppComponent
import com.co.geo.rankertmdb.movielist.di.component.DaggerAppComponent
import com.co.geo.rankertmdb.movielist.di.module.ApplicationModule

class MoviesApp: Application() {

    lateinit var component: AppComponent
    override fun onCreate() {
        super.onCreate()

        //DI
        component = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()


    }
}