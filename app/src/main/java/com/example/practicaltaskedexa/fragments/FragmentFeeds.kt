package com.example.practicaltaskedexa.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.practicaltaskedexa.databinding.FeedFragmentBinding

class FragmentFeeds : Fragment() {

    private lateinit var binding: FeedFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FeedFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }
}