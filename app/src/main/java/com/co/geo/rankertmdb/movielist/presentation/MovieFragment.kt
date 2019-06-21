package com.co.geo.rankertmdb.movielist.presentation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import com.bumptech.glide.Glide

import com.co.geo.rankertmdb.R
import com.co.geo.rankertmdb.movielist.data.model.entity.MovieEntity
import com.co.geo.rankertmdb.movielist.util.MoviesApp
import kotlinx.android.synthetic.main.fragment_movie.*
import javax.inject.Inject

class MovieFragment : Fragment() {
    companion object {

        val ARG_MOVIE = "ARG_MOVIE"
        val ARG_BASEURL = "ARG_BASE_URL"

        fun newInstance(movie: MovieEntity, imgBaseUrl: String) : Fragment {
            // Nos creamos el fragment
            val fragment = MovieFragment()

            //Nos creamos los arg del fragment
            val arguments = Bundle()
            arguments.putParcelable(ARG_MOVIE, movie)
            arguments.putString(ARG_BASEURL, imgBaseUrl)

            //Asignamos los arg al fragment
            fragment.arguments = arguments

            //Devolvermos el fragment
            return fragment
        }
    }

    lateinit var movieListViewModel: MovieListViewModel

    @Inject
   lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var movie: MovieEntity
    lateinit var imgBaseUrl: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setUpViewModel()
        super.onCreateView(inflater, container, savedInstanceState)
        var root = inflater?.inflate(R.layout.fragment_movie, container, false)
        return  root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            movie = it.getParcelable<MovieEntity>(ARG_MOVIE)
            imgBaseUrl = it.getString(ARG_BASEURL)
            updateView(movie)
            setUpUi()
        }

    }

    private fun setUpUi() {
        rating_bar.setOnRatingBarChangeListener { ratingBar, fl, b ->
            movie.rank = fl
            val t = Thread(Runnable {
                val inserted = movieListViewModel.insertOne(movie)
                Log.i("RANGKING", "fl $fl b $b inserted $inserted")
            })
            t.start()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
        }
    }


    private fun inject() {
        (activity?.application as MoviesApp).component.inject(this)
    }

    private fun setUpViewModel() {
        movieListViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel::class.java)
        bindEvents()
    }

    private fun bindEvents() {
        movieListViewModel.movieState.observe(this, Observer {
            it?.let {
               it.rank?.let {
                   rating_bar.rating = it
               }
            }
        })
    }

    fun updateView(movie: MovieEntity) {

        movieListViewModel.findOne(movie) //solo actualiza el rank

        txt_title.text = movie.title
        txt_overview.text = movie.overview

        var url = "${imgBaseUrl}${movie.backdropPath}"
        Glide.with(backdrop_Img)
            .load(url)
            .into(backdrop_Img)

        url = "${imgBaseUrl}${movie.posterPath}"
        Glide.with(poster_Img)
            .load(url)
            .into(poster_Img)
    }
}
