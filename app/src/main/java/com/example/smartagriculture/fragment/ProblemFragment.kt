package com.example.smartagriculture.fragment

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.base.BaseFragment
import com.example.common.bean.BeanList
import com.example.common.clickNoRepeat
import com.example.common.model.NoHttpRx
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.CommentExpandAdapter
import com.example.smartagriculture.bean.CommentItem
import com.example.smartagriculture.databinding.FragmentProblemBinding
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.MineViewModel
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_problem.*
import kotlinx.android.synthetic.main.title_item.*
import java.util.zip.Inflater

/**
 * A simple [Fragment] subclass.
 */
class ProblemFragment : BaseFragment<MineViewModel, FragmentProblemBinding>() {

    lateinit var problemAdapter: CommentExpandAdapter
    lateinit var mLRecyclerViewAdapter: LRecyclerViewAdapter
    lateinit var list: MutableList<CommentItem>

    override fun initLayout(): Int {
        return R.layout.fragment_problem
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(MineViewModel::class.java)
        textView.text = getString(R.string.problem)
        problemAdapter = CommentExpandAdapter(requireContext(), problem_recycler)
        mLRecyclerViewAdapter = LRecyclerViewAdapter(problemAdapter)
        problem_recycler.adapter = mLRecyclerViewAdapter

        problem_recycler.layoutManager = LinearLayoutManager(requireContext())
        val view=LayoutInflater.from(context).inflate(R.layout.header, null, false)
        mLRecyclerViewAdapter.addHeaderView(view)
        list = mutableListOf()
    }

    override fun lazyLoadData() {

        viewModel.noHttpRx = NoHttpRx(this)
        viewModel.getProblem("1")
    }

    override fun setListener() {
        super.setListener()
        problem_recycler.setPullRefreshEnabled(false)
        back.clickNoRepeat {
            nav().navigateUp()
        }
    }

    override fun toData(flag: String?, `object`: String?) {
        super.toData(flag, `object`)
        when (flag) {
            "常见问题" -> {
                val beanList = Gson().fromJson(`object`, BeanList::class.java)
                if (beanList.data.isEmpty()) {
                    return
                } else {
                    list.clear()
                    beanList.data.forEach {
                        list.add(CommentItem(it.question))
                        list.add(CommentItem(it.answer,it.remarks))
                    }
                    problemAdapter.setItems(list)
                }
            }
            else -> {
            }
        }
    }

}
