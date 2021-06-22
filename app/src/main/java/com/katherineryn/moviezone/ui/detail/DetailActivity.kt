package com.katherineryn.moviezone.ui.detail

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.katherineryn.moviezone.R
import com.katherineryn.moviezone.core.data.source.remote.network.NetworkConst.IMAGE_URL
import com.katherineryn.moviezone.core.domain.model.Film
import com.katherineryn.moviezone.databinding.ActivityDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBarTitle("")
        showProgressBar(true)

        binding.tvOverview.movementMethod = ScrollingMovementMethod()

        val itemFilm = intent.getParcelableExtra<Film>(EXTRA_DATA)
        if (itemFilm != null) {
            showProgressBar(false)
            populateFilmDetail(itemFilm)
        }

        binding.fabShare.setOnClickListener { share() }
    }

    private fun populateFilmDetail(film: Film) {
        with(binding) {
            tvTitle.text = film.title
            tvPopularity.text = film.popularity.toString()
            tvRating.text = film.voteAverage.toString()
            tvVoteCount.text = resources.getString(R.string.vote_count, film.voteCount.toString())
            tvRelease.text = detailViewModel.parseDate(film.releaseDate)
            tvOverview.text = film.overview
            imagePoster.loadImage(IMAGE_URL + film.poster)

            var favState = film.isFav
            setFavState(favState)

            fabFavorite.setOnClickListener {
                favState = !favState
                detailViewModel.setFavFilm(film, favState)
                setFavState(favState)

                val favText = if (favState) {
                    "${film.title} added to Favorite"
                } else {
                    "${film.title} removed from Favorite"
                }

                Toast.makeText(this@DetailActivity, favText, Toast.LENGTH_SHORT).show()
            }

            setActionBarTitle(film.title)
        }
    }

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(this)
    }

    private fun setFavState(state: Boolean) {
        if (state) {
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite_yellow)
        } else {
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border_yellow)
        }
    }

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun showProgressBar(visibilityStatus: Boolean) {
        // if progress bar visible, content invisible
        binding.progressBar.isVisible = visibilityStatus
        binding.contentDetail.isInvisible = visibilityStatus
    }

    private fun share() {
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder.from(this).apply {
            setType(mimeType)
            setChooserTitle(getString(R.string.shareTitle))
            setText(getString(R.string.shareText))
            startChooser()
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}