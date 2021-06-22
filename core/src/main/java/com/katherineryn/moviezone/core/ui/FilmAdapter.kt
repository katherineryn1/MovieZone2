package com.katherineryn.moviezone.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.katherineryn.moviezone.core.R
import com.katherineryn.moviezone.core.data.source.remote.network.NetworkConst.IMAGE_URL
import com.katherineryn.moviezone.core.databinding.ItemCvFilmBinding
import com.katherineryn.moviezone.core.domain.model.Film
import com.katherineryn.moviezone.core.utils.DiffUtils

class FilmAdapter : RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {

    private var listData = ArrayList<Film>()
    var onItemClick: ((Film) -> Unit)? = null

    fun setData(newListData: List<Film>?) {
        if (newListData == null) return
        val diffUtilCallback = DiffUtils(listData, newListData)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        listData.clear()
        listData.addAll(newListData)
        diffResult.dispatchUpdatesTo(this)
    }

    fun getSwipedData(swipedPosition: Int): Film = listData[swipedPosition]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val cvFilmBinding =
            ItemCvFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(cvFilmBinding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) =
        holder.bind(listData[position])

    override fun getItemCount(): Int = listData.size

    inner class FilmViewHolder(private val binding: ItemCvFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Film) {
            with(binding) {
                tvTitle.text = film.title
                tvRating.text = film.voteAverage.toString()
                imagePoster.loadImage(IMAGE_URL + film.poster)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[bindingAdapterPosition])
            }
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
}