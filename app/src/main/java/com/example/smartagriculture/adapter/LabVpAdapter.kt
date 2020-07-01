package com.example.smartagriculture.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.common.bean.BeanDataList
import com.example.smartagriculture.fragment.ChatMailListFragment
import com.example.smartagriculture.fragment.WarningListFragment
import com.example.common.data.Identification


class LabVpAdapter(
    fm: FragmentManager?,
    private val mTitles: List<Any>,
    private val parkId:String,
    private val flag: Int
) :
    FragmentPagerAdapter(fm!!,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getItem(position: Int): Fragment {
        if (Identification.WARNINGLIST==flag) {
            val beanDataList:BeanDataList= mTitles[position] as BeanDataList
            return WarningListFragment.newInstance(beanDataList, flag,parkId)!!
        }
        if (Identification.CHAT==flag) {
            return ChatMailListFragment.newInstance(mTitles[position].toString(), flag)!!
        }
        return ChatMailListFragment.newInstance(mTitles[position].toString(), flag)!!
    }


    override fun getCount(): Int {
        return mTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if (Identification.WARNINGLIST==flag) {
            val beanDataList:BeanDataList= mTitles[position] as BeanDataList
            return beanDataList.label
        }
        if (Identification.CHAT==flag) {
            return mTitles[position].toString()
        }
        return mTitles[position].toString()
    }

}