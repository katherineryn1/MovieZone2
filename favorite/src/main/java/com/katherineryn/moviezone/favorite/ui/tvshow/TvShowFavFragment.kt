package com.katherineryn.moviezone.favorite.ui.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.katherineryn.moviezone.core.domain.model.Film
import com.katherineryn.moviezone.core.ui.FilmAdapter
import com.katherineryn.moviezone.favorite.R
import com.katherineryn.moviezone.favorite.databinding.FragmentTvShowFavBinding
import com.katherineryn.moviezone.favorite.ui.FavoriteViewModel
import com.katherineryn.moviezone.ui.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel

class TvShowFavFragment : Fragment() {

    private var _fragmentTvShowFavFragment: FragmentTvShowFavBinding? = null
    private val binding get() = _fragmentTvShowFavFragment

    private lateinit var tvShowAdapter: FilmAdapter
    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentTvShowFavFragment = FragmentTvShowFavBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemTouchHelper.attachToRecyclerView(binding?.rvFavTvShow)

        tvShowAdapter = FilmAdapter()
        tvShowAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }

        viewModel.getFavTvShows().observe(viewLifecycleOwner, favTvShowObserver)

        with(binding?.rvFavTvShow) {
            this?.layoutManager = GridLayoutManager(context, 2)
            this?.setHasFixedSize(true)
            this?.adapter = tvShowAdapter
        }
    }

    private val favTvShowObserver = Observer<List<Film>> { favTvShows ->
        if (favTvShows.isNullOrEmpty()) {
            showEmptyText(true)
        } else {
            showEmptyText(false)
        }
        tvShowAdapter.setData(favTvShows)
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.bindingAdapterPosition
                val tvShowEntity = tvShowAdapter.getSwipedData(swipedPosition)
                var state = tvShowEntity.isFav
                viewModel.setFavFilm(tvShowEntity, !state)
                state = !state

                val snackBar = Snackbar.make(requireView(), R.string.msg_undo, Snackbar.LENGTH_LONG)
                snackBar.setAction(R.string.msg_ok) {
                    viewModel.setFavFilm(tvShowEntity, !state)
                }
                snackBar.show()
            }
        }
    })

    private fun showEmptyText(state: Boolean) {
        // if empty text visible, recyclerview invisible
        binding?.emptyFav?.isVisible = state
        binding?.rvFavTvShow?.isInvisible = state
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.rvFavTvShow?.adapter = null
        _fragmentTvShowFavFragment = null
    }
}