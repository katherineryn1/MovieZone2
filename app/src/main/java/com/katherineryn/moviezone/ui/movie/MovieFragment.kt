package com.katherineryn.moviezone.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.katherineryn.moviezone.R
import com.katherineryn.moviezone.core.data.Resource
import com.katherineryn.moviezone.core.domain.model.Film
import com.katherineryn.moviezone.core.ui.FilmAdapter
import com.katherineryn.moviezone.core.utils.SortUtils.NEWEST
import com.katherineryn.moviezone.core.utils.SortUtils.OLDEST
import com.katherineryn.moviezone.core.utils.SortUtils.RANDOM
import com.katherineryn.moviezone.databinding.FragmentMovieBinding
import com.katherineryn.moviezone.ui.MainActivity
import com.katherineryn.moviezone.ui.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private val movieViewModel: MovieViewModel by viewModel()
    private lateinit var movieAdapter: FilmAdapter

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            (activity as MainActivity).setActionBarTitle("Movies")

            showProgressBar(true)
            movieAdapter = FilmAdapter()

            movieAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
            }

            movieViewModel.getMovies(NEWEST).observe(viewLifecycleOwner, movieObserver)

            with(binding?.rvMovie) {
                this?.layoutManager = GridLayoutManager(context, 2)
                this?.setHasFixedSize(true)
                this?.adapter = movieAdapter
            }
        }
    }

    private val movieObserver = Observer<Resource<List<Film>>> { movies ->
        if (movies != null) {
            when (movies) {
                is Resource.Loading -> showProgressBar(true)
                is Resource.Success -> {
                    showProgressBar(false)
                    movieAdapter.setData(movies.data)
                }
                is Resource.Error -> {
                    showProgressBar(false)
                    Toast.makeText(
                        context,
                        "Something wrong when retrieve the data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        activity?.menuInflater?.inflate(R.menu.sort_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""
        when (item.itemId) {
            R.id.action_newest -> sort = NEWEST
            R.id.action_oldest -> sort = OLDEST
            R.id.action_random -> sort = RANDOM
        }

        movieViewModel.getMovies(sort).observe(viewLifecycleOwner, movieObserver)
        item.isChecked = true

        return super.onOptionsItemSelected(item)
    }

    private fun showProgressBar(state: Boolean) {
        // if progress bar visible, recyclerview invisible
        binding?.progressBar?.isVisible = state
        binding?.rvMovie?.isInvisible = state
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.rvMovie?.adapter = null
        _binding = null
    }
}