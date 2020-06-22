package com.example.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.androidkun.xtablayout.XTabLayout
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter


/**
 * A simple [Fragment] subclass.
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment() {

    lateinit var mTitles: List<String>

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
    open fun setListener() {
    }


    open fun getTabView(position: Int): View {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.tab_top, null)
        val txtTitle = view.findViewById<TextView>(R.id.textView9)
        txtTitle.text = mTitles[position]
        if (position == 0) {
            txtTitle.background = resources.getDrawable(R.drawable.tab_bg, null)
        }
        return view
    }

    open fun getTabViewChat(position: Int): View {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.tab_top_chat, null)
        val txtTitle = view.findViewById<TextView>(R.id.textView9)
        txtTitle.text = mTitles[position]
        if (position == 0) {
            txtTitle.background = resources.getDrawable(R.drawable.tab_bg, null)
        }
        return view
    }

    open fun changeTabSelect(tab: XTabLayout.Tab, view_pager: ViewPager) {
        val view = tab.customView
        val txtTitle = view?.findViewById<TextView>(R.id.textView9)
        if (txtTitle != null) {
            txtTitle.background = resources.getDrawable(R.drawable.tab_bg, null)
            view_pager.currentItem = tab.position
        }
    }

    open fun changeTabNormal(tab: XTabLayout.Tab) {
        val view = tab.customView
        val txtTitle = view?.findViewById<TextView>(R.id.textView9)
        if (txtTitle != null) {
            txtTitle.background = null
        }
    }

    override fun onPause() {
        super.onPause()
        hideSoftKeyboard(requireActivity())
    }

}
