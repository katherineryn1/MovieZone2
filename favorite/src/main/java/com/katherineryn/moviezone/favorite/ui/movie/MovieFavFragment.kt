package com.katherineryn.moviezone.favorite.ui.movie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.katherineryn.moviezone.core.domain.model.Film
import com.katherineryn.moviezone.core.ui.FilmAdapter
import com.katherineryn.moviezone.favorite.R
import com.katherineryn.moviezone.favorite.databinding.FragmentMovieFavBinding
import com.katherineryn.moviezone.favorite.ui.FavoriteViewModel
import com.katherineryn.moviezone.ui.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFavFragment : Fragment() {

    private var _fragmentMovieFavBinding: FragmentMovieFavBinding? = null
    private val binding get() = _fragmentMovieFavBinding

    private lateinit var movieAdapter: FilmAdapter
    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentMovieFavBinding = FragmentMovieFavBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemTouchHelper.attachToRecyclerView(binding?.rvFavMovie)

        movieAdapter = FilmAdapter()
        movieAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }

        viewModel.getFavMovies().observe(viewLifecycleOwner, favMovieObserver)

        with(binding?.rvFavMovie) {
            this?.layoutManager = GridLayoutManager(context, 2)
            this?.setHasFixedSize(true)
            this?.adapter = movieAdapter
        }
    }

    private val favMovieObserver = Observer<List<Film>> { favMovies ->
        if (favMovies.isNullOrEmpty()) {
            showEmptyText(true)
        } else {
            showEmptyText(false)
        }
        movieAdapter.setData(favMovies)
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.bindingAdapterPosition
                val movieEntity = movieAdapter.getSwipedData(swipedPosition)
                var state = movieEntity.isFav
                viewModel.setFavFilm(movieEntity, !state)
                state = !state

                val snackBar = Snackbar.make(requireView(), R.string.msg_undo, Snackbar.LENGTH_LONG)
                snackBar.setAction(R.string.msg_ok) {
                    viewModel.setFavFilm(movieEntity, !state)
                }
                snackBar.show()
            }
        }
    })

    private fun showEmptyText(state: Boolean) {
        // if empty text visible, recyclerview invisible
        binding?.emptyFav?.isVisible = state
        binding?.rvFavMovie?.isInvisible = state
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.rvFavMovie?.adapter = null
        _fragmentMovieFavBinding = null
    }
}