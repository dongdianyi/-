package com.example.smartagriculture.fragment

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.common.base.BaseFragment
import com.example.common.base.BaseViewModel

/**
 * A simple [Fragment] subclass.
 */
abstract class BaseDropDownFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseFragment<VM, DB>(){

    override fun toData(flag: String?, data: String?) {
        super.toData(flag, data)
    }

    override fun fail(isNetWork: Boolean, flag: String?, t: Throwable?) {
        super.fail(isNetWork, flag, t)
    }

}
