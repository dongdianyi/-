package com.example.smartagriculture.fragment

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.common.base.BaseFragment
import com.example.common.ToastUtil
import com.example.common.hideSoftKeyboard
import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.FragmentSearchBinding
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.title_item_two.*


/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>() {


    var history = mutableListOf<String>()
    override fun initLayout(): Int {
        return R.layout.fragment_search
    }

    override fun initView(view: View) {
        textView.text=resources.getText(R.string.search)
    }

    override fun initData() {
        for (i in 0..10) {
            history.add("哈哈哈哈哈哈哈哈")
            history.add("嘿嘿嘿")
        }
//往容器内添加TextView数据

        //往容器内添加TextView数据
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(10, 10, 10, 10)
        if (flowlayout != null) {
            flowlayout.removeAllViews()
        }
        for (i in 0 until history.size) {
            val tv = TextView(requireContext())
            tv.setPadding(28, 10, 28, 10)
            tv.textSize=12f
            tv.setTextColor(Color.rgb(34,34,34))
            tv.text = history[i]
            tv.maxEms = 10
            tv.setSingleLine()
            tv.setBackgroundResource(R.drawable.cycle_gray)
            tv.layoutParams = layoutParams
            flowlayout.addView(tv, layoutParams)
            tv.setOnClickListener {
                ToastUtil(tv.text)
            }
        }
    }

    override fun setListener() {
        back.setOnClickListener {
            hideSoftKeyboard(requireActivity())
            nav().navigateUp()
        }
    }

}
