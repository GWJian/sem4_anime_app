package com.gwj.sem4_anime_app.ui.tabContainer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.google.android.material.tabs.TabLayoutMediator
import com.gwj.sem4_anime_app.R
import com.gwj.sem4_anime_app.databinding.FragmentTabContainerBinding
import com.gwj.sem4_anime_app.ui.adapter.FragmentAdapter
import com.gwj.sem4_anime_app.ui.home.HomeFragment
import com.gwj.sem4_anime_app.ui.profile.ProfileFragment
import com.gwj.sem4_anime_app.ui.random.RandomAnimeFragment
import com.gwj.sem4_anime_app.ui.search.SearchFragment
import com.gwj.sem4_anime_app.ui.seasonal.SeasonalFragment

class TabContainerFragment : Fragment() {

    private lateinit var binding: FragmentTabContainerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTabContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vpContainer.adapter = FragmentAdapter(
            this,
            listOf(HomeFragment(), SearchFragment(), SeasonalFragment(), ProfileFragment() )
        )

        setFragmentResultListener("profile") { _, result ->
            binding.vpContainer.setCurrentItem(3, false)
        }

        TabLayoutMediator(binding.tlTabs, binding.vpContainer) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Home"
                    tab.setIcon(R.drawable.ic_home)
                }

                1 -> {
                    tab.text = "Search"
                    tab.setIcon(R.drawable.ic_search)
                }

                2 -> {
                    tab.text = "Season"
                    tab.setIcon(R.drawable.ic_season_list)
                }

                3 -> {
                    tab.text = "Profile"
                    tab.setIcon(R.drawable.ic_profile)
                }

            }
        }.attach()

        binding.vpContainer.isUserInputEnabled = false
    }
}