package com.katherineryn.moviezone.favorite.ui

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.katherineryn.moviezone.favorite.R
import com.katherineryn.moviezone.favorite.ui.movie.MovieFavFragment
import com.katherineryn.moviezone.favorite.ui.tvshow.TvShowFavFragment

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val tabTitles = intArrayOf(
        R.string.title_movie,
        R.string.title_tvshow
    )

    private val fragment: List<Fragment> = listOf(
        MovieFavFragment(),
        TvShowFavFragment()
    )

    override fun getPageTitle(position: Int): CharSequence {
        return context.getString(tabTitles[position])
    }

    override fun getCount() = tabTitles.size

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }
}