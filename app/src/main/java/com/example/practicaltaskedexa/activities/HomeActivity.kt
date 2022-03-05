package com.example.practicaltaskedexa.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.practicaltaskedexa.databinding.ActivityHomeBinding
import android.R
import com.example.practicaltaskedexa.adapters.ViewPagerAdapter
import com.example.practicaltaskedexa.fragments.FragmentFeeds
import com.example.practicaltaskedexa.fragments.FragmentProfile


class HomeActivity : AppCompatActivity() {

    private lateinit var databinding: ActivityHomeBinding
    private var userID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        databinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(databinding.root)

        userID = intent.getIntExtra("userid", -1)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(FragmentProfile(), "Profile")
        adapter.addFragment(FragmentFeeds(), "Feeds")
        databinding.viewPagerHome.adapter = adapter
        databinding.tabLayoutHome.setupWithViewPager(databinding.viewPagerHome)
    }
}