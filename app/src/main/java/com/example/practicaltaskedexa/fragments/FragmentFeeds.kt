package com.example.practicaltaskedexa.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.practicaltaskedexa.adapters.ViewPagerAdapter
import com.example.practicaltaskedexa.databinding.FeedFragmentBinding

class FragmentFeeds : Fragment() {

    private lateinit var binding: FeedFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FeedFragmentBinding.inflate(inflater, container, false)

        val adapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        val argsAll = Bundle()
        argsAll.putString("city", "")
        val argsChicago = Bundle()
        argsChicago.putString("city", "Chicago")
        val argsNewYork = Bundle()
        argsNewYork.putString("city", "NewYork")
        val argsLosAngeles = Bundle()
        argsLosAngeles.putString("city", "Los Angeles")
        adapter.addFragment(FragmentEmployee("All"), "All", argsAll)
        adapter.addFragment(FragmentEmployee("Chicago"), "Chicago", argsChicago)
        adapter.addFragment(FragmentEmployee("NewYork"), "NewYork", argsNewYork)
        adapter.addFragment(FragmentEmployee("Los Angeles"), "Los Angeles", argsLosAngeles)
        binding.viewPagerFeed.adapter = adapter
        binding.tabLayoutFeed.setupWithViewPager(binding.viewPagerFeed)

        return binding.root
    }
}