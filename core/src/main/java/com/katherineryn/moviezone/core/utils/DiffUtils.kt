package com.katherineryn.moviezone.core.utils

import androidx.recyclerview.widget.DiffUtil
import com.katherineryn.moviezone.core.domain.model.Film

class DiffUtils(private val oldList: List<Film>, private val newList: List<Film>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}