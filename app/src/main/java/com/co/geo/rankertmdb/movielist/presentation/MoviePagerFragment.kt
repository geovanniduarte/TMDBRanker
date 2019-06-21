package com.co.geo.rankertmdb.movielist.presentation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.co.geo.rankertmdb.R
import com.co.geo.rankertmdb.movielist.data.model.entity.MovieEntity
import com.co.geo.rankertmdb.movielist.util.MoviesApp
import kotlinx.android.synthetic.main.fragment_movie_pager.*
import javax.inject.Inject


class MoviePagerFragment: Fragment(), AdapterView.OnItemSelectedListener {

    companion object {

        val ARG_MOVIE = "ARG_MOVIE"

        fun newInstance(movieIndex: Int) : MoviePagerFragment {
            val arguments = Bundle()
            arguments.putInt(ARG_MOVIE, movieIndex)
            val fragment = MoviePagerFragment()
            fragment.arguments = arguments

            return fragment
        }
    }

    lateinit var movieListViewModel: MovieListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            setUpViewModel()
            super.onCreateView(inflater, container, savedInstanceState)
            return inflater.inflate(R.layout.fragment_movie_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val initialMovieIndex = arguments?.getInt(ARG_MOVIE, 0)

        if (initialMovieIndex != null) {
            moveToMovie(initialMovieIndex)
            updateMovieInfo(initialMovieIndex)
        }

        if (movieListViewModel.imgConfig == null) {
            movieListViewModel.getImgConfig()
        } else {
            movieListViewModel.getAllMovies(movieListViewModel.selectedYear)
        }

        ArrayAdapter.createFromResource(
            activity,
            R.array.years_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            year_spinner.adapter = adapter
        }

        year_spinner.onItemSelectedListener = this

    }

    private fun setUpPager() {

            val adapter = object: FragmentStatePagerAdapter(fragmentManager) {

                private var lastPosition = -1
                private var right: Boolean = false

                override fun getCount() = movieListViewModel.totalResults

                override fun getItem(globalPosition: Int): Fragment {
                    right = globalPosition > lastPosition
                    var localList: List<MovieEntity>? = null


                    val localPage = (globalPosition / 20)
                    val localPosition = globalPosition - ((localPage) * 20)

                    if (localPosition == 18) {
                        movieListViewModel.page++
                    }

                    lastPosition = globalPosition

                    return MovieFragment.newInstance(
                        movieListViewModel.moviesList[localPage].get(localPosition),
                        movieListViewModel.imgConfig!!
                    )

                    return MovieFragment.newInstance(
                        MovieEntity(0,
                            getString(R.string.pager_title_empty),
                            "",
                            "", "", 0f), "")
                }

                override fun getPageTitle(position: Int): CharSequence? {
                    val page = (position / 20)
                    movieListViewModel.moviesList[page]?.let {
                        val localPosition = position - ((page) * 20)
                        val movieEntity = it[localPosition];
                        return movieEntity.title
                    }
                    return getString(R.string.pager_title_empty)
                }
            }

            view_pager.adapter = adapter

            view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                }

                override fun onPageSelected(globalPosition: Int) {
                    Log.i("getItem2", "globalPosition $globalPosition")
                    val localPosition = globalPosition - ((((globalPosition / 20) + 1) - 1) * 20)
                    updateMovieInfo(localPosition)
                }
            })

            //view_pager.adapter?.notifyDataSetChanged()
    }

    private fun inject() {
        (activity?.application as MoviesApp).component.inject(this)
    }

    private fun setUpViewModel() {
        movieListViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel::class.java)
        bindEvents()
    }

    private fun bindEvents() {
        movieListViewModel.movieListState.observe(this, Observer {
            setUpPager()
        })

        movieListViewModel.isLoadingState.observe(this, Observer { isLoading ->
            isLoading?.let {
                showLoading(it)
            }
        })

        movieListViewModel.changeState.observe(this, Observer {
           it?.let {
               if (it) {
                   view_pager.adapter?.notifyDataSetChanged()
               }
           }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        movieListLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    fun updateMovieInfo(position: Int) {
        if (activity is AppCompatActivity) {
            val supportActionBar: ActionBar? = (activity as? AppCompatActivity?)?.supportActionBar
            val page = (position / 20)
            if (!movieListViewModel.moviesList.isEmpty()) {
                movieListViewModel.moviesList[page]?.let {
                    val localPosition = position - ((page) * 20)
                    supportActionBar?.title = it[localPosition]?.title
                }
            }
        }
    }

    fun moveToMovie(position: Int) {
        view_pager.currentItem = position
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.pager_navigation, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.previous -> {
            view_pager.currentItem = view_pager.currentItem - 1
            true
        }
        R.id.next -> {
            view_pager.currentItem = view_pager.currentItem + 1
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        val previousMenu = menu?.findItem(R.id.previous)
        val nextMenu = menu?.findItem(R.id.next)

       view_pager.adapter?.let {
            previousMenu?.isEnabled = view_pager.currentItem > 0
            nextMenu?.isEnabled = view_pager.currentItem < it.count -1
       }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
        p0?.let {
            val selectedYear = it.getItemAtPosition(pos).toString().toInt()
            movieListViewModel.selectedYear = selectedYear
            movieListViewModel.getAllMovies(selectedYear)
        }
    }
}