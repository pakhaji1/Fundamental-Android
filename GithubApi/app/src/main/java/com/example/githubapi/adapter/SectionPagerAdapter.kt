package com.example.githubapi.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubapi.FollowerFragment
import com.example.githubapi.FollowingFragment

class SectionPagerAdapter(activity: AppCompatActivity, bundle: Bundle,) : FragmentStateAdapter(activity) {

    private var fragmentBundle:Bundle = bundle

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = fragmentBundle
        return fragment as Fragment

    }

}