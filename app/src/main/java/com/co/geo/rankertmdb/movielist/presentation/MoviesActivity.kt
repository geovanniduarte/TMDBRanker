package com.co.geo.rankertmdb.movielist.presentation


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.co.geo.rankertmdb.R

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.findFragmentById(R.id.movies_fragment) == null) {
            val fragment = MoviePagerFragment.newInstance(0)
            supportFragmentManager.beginTransaction()
                .add(R.id.movies_fragment, fragment)
                .commit()
        }
    }
}
