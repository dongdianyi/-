package com.example.smartagriculture.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.smartagriculture.fragment.WarningListFragment


class LabVpAdapter(
    fm: FragmentManager?,
    private val mTitles: List<String>,
    private val flag: String
) :
    FragmentPagerAdapter(fm!!,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getItem(position: Int): Fragment {
      return WarningListFragment.newInstance(mTitles[position], flag)!!
    }


    override fun getCount(): Int {
        return mTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }

}