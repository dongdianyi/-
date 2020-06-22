package com.example.smartagriculture.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.common.LogUtil
import com.example.smartagriculture.fragment.ChatMailListFragment
import com.example.smartagriculture.fragment.WarningListFragment
import com.example.smartagriculture.util.Identification


class LabVpAdapter(
    fm: FragmentManager?,
    private val mTitles: List<String>,
    private val flag: Int
) :
    FragmentPagerAdapter(fm!!,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getItem(position: Int): Fragment {
        if (Identification.WARNINGLIST==flag) {
            return WarningListFragment.newInstance(mTitles[position], flag)!!
        }
        if (Identification.CHAT==flag) {
            return ChatMailListFragment.newInstance(mTitles[position], flag)!!
        }
        return WarningListFragment.newInstance(mTitles[position], flag)!!
    }


    override fun getCount(): Int {
        return mTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }

}