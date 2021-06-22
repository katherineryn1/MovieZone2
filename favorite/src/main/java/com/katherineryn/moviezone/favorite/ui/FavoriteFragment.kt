package com.katherineryn.moviezone.favorite.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.katherineryn.moviezone.favorite.databinding.FragmentFavoriteBinding
import com.katherineryn.moviezone.favorite.di.favModule
import com.katherineryn.moviezone.ui.MainActivity
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {
    private var _favoriteFragmentBinding: FragmentFavoriteBinding? = null
    private val binding get() = _favoriteFragmentBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _favoriteFragmentBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(favModule)
        (activity as MainActivity).setActionBarTitle("Favorite")

        val sectionPagerAdapter = SectionsPagerAdapter(context as Context, childFragmentManager)
        binding.viewPager.adapter = sectionPagerAdapter
        binding.tab.setupWithViewPager(binding.viewPager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _favoriteFragmentBinding = null
    }
}