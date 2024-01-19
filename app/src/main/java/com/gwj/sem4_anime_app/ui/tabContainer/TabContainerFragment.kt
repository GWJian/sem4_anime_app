package com.gwj.sem4_anime_app.ui.tabContainer

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
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

    //======================== Exit App Start ========================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                // Show dialog to confirm exit app
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.exit_app))
                    .setMessage(getString(R.string.are_you_sure))
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        // Exit the app
                        activity?.finish()
                    }
                    .setNegativeButton(getString(R.string.no), null)
                    .show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
    //======================== Exit App End ========================================

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

                    tab.setIcon(R.drawable.ic_home)
                    tab.text = getString(R.string.shortcut_home_short_label)
                }

                1 -> {

                    tab.setIcon(R.drawable.ic_search)
                    tab.text = getString(R.string.shortcut_search_short_label)
                }

                2 -> {

                    tab.setIcon(R.drawable.ic_season_list)
                    tab.text = getString(R.string.shortcut_seasonal_short_label)
                }

                3 -> {

                    tab.setIcon(R.drawable.ic_profile)
                    tab.text = getString(R.string.profile)
                }

            }
        }.attach()

        binding.vpContainer.isUserInputEnabled = false
    }
}