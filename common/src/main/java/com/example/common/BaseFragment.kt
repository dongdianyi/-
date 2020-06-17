package com.example.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter

/**
 * A simple [Fragment] subclass.
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment() {


    lateinit var viewModel: VM
    lateinit var dataBinding: DB
    lateinit var mLRecycleViewAdapter: LRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, initLayout(), container, false)
        dataBinding.lifecycleOwner = this
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initData()
        setListener()
    }

    abstract fun initLayout(): Int
    abstract fun initView(view: View)
    abstract fun initData()
    open fun setListener(){
    }

}
