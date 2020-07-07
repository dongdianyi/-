package com.example.common.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.ViewPager
import com.androidkun.xtablayout.XTabLayout
import com.example.common.R
import com.example.common.bean.BeanDataList
import com.example.common.hideSoftKeyboard
import com.example.common.model.NoHttpRx
import com.example.common.myview.CustomDialog
import com.example.common.view.IView
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.liqi.nohttputils.interfa.OnDialogGetListener


/**
 * A simple [Fragment] subclass.
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment() ,
     IView ,OnDialogGetListener{


    //是否第一次加载
    private var isFirst: Boolean = true

    lateinit var mTitles: MutableList<String>
    lateinit var mTitleWarning: MutableList<BeanDataList>
    private var mDialog: Dialog? = null
    lateinit var noHttpRx:NoHttpRx

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
        noHttpRx= NoHttpRx(this)
        initView(savedInstanceState)
        onVisible()
        initData()
        setListener()
    }

    abstract fun initLayout(): Int
    abstract fun initView(savedInstanceState:Bundle?)
    open fun initData(){}
    open fun setListener() {
    }


    open fun getTabView(position: Int): View {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.tab_top, null)
        val txtTitle = view.findViewById<TextView>(R.id.textView9)
        txtTitle.text = mTitleWarning[position].label
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

    override fun onResume() {
        super.onResume()
        onVisible()

    }
    override fun onPause() {
        super.onPause()
        hideSoftKeyboard(requireActivity())
    }

    override fun toData(flag: String?, `object`: String?) {
    }

    override fun fail(isNetWork: Boolean, flag: String?, t: Throwable?) {
    }
    override fun getDialog(): Dialog {
        if (null == mDialog) {
            mDialog = CustomDialog(
                context,
                R.style.CustomDialog
            )
        }
        return mDialog as Dialog
    }

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            lazyLoadData()
            isFirst = false
        }
    }
    /**
     * 懒加载
     */
    abstract fun lazyLoadData()
}
