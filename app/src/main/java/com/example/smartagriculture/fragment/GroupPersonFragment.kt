package com.example.smartagriculture.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.base.BaseFragment
import com.example.common.bean.Bean
import com.example.common.bean.Have
import com.example.common.clickNoRepeat
import com.example.common.data.Identification
import com.example.common.data.Identification.Companion.ONE
import com.example.common.model.NoHttpRx
import com.example.common.setNbOnItemClickListener
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.ChatAdapter
import com.example.smartagriculture.databinding.FragmentGroupPersonBinding
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.ChatViewModel
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_group_person.*
import kotlinx.android.synthetic.main.title_item_two.*

/**
 * A simple [Fragment] subclass.
 */
class GroupPersonFragment : BaseFragment<ChatViewModel, FragmentGroupPersonBinding>() {

    lateinit var chatUser: String
    lateinit var list: MutableList<Have>
    lateinit var chatAdapter: ChatAdapter

    override fun initLayout(): Int {
        return R.layout.fragment_group_person
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(ChatViewModel::class.java)
        textView.text = getString(R.string.group_person)
        chatUser = arguments?.getString("chatUser").toString()
        list= mutableListOf()
        chatAdapter =
            ChatAdapter(requireContext(), R.layout.chat_item,
                Identification.GROUP_PERSON)
        mLRecycleViewAdapter = LRecyclerViewAdapter(chatAdapter)
        chat_recycler.adapter = mLRecycleViewAdapter

        val divider: DividerDecoration = DividerDecoration.Builder(requireContext())
            .setHeight(R.dimen.mm_2)
            .setColorResource(R.color.bg)
            .build()
        chat_recycler.addItemDecoration(divider)
        chat_recycler.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun lazyLoadData() {
        viewModel.noHttpRx = NoHttpRx(this)
        viewModel.getGroupPerson(chatUser)
    }

    override fun setListener() {
        mLRecycleViewAdapter.setNbOnItemClickListener { view, position ->
            var bundle=Bundle()
            bundle.putString("chatUser",list[position].userid)
            bundle.putString("category",ONE)
            viewModel.toChatDetails(view,bundle)
        }
        back.clickNoRepeat {
            nav().navigateUp()
        }
    }

    override fun toData(flag: String?, `object`: String?) {
        super.toData(flag, `object`)
        when (flag) {
            "群成员" -> {
                val bean= Gson().fromJson(`object`,Bean::class.java)
                list.clear()
                list.addAll(bean.data.have)
                chatAdapter.setDataList(bean.data.have)
            }
            else -> {
            }
        }

    }
}
