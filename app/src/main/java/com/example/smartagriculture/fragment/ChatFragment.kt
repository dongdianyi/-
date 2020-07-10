package com.example.smartagriculture.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.amap.api.mapcore.util.it
import com.example.common.LogUtil
import com.example.common.base.BaseFragment
import com.example.common.bean.BeanDataList
import com.example.common.bean.BeanList
import com.example.common.clickNoRepeat
import com.example.common.setNbOnItemClickListener

import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.ChatAdapter
import com.example.smartagriculture.databinding.FragmentChatBinding
import com.example.common.data.Identification
import com.example.common.model.NoHttpRx
import com.example.smartagriculture.viewmodel.ChatViewModel
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_chat.*

/**
 * A simple [Fragment] subclass.
 */
class ChatFragment : BaseFragment<ChatViewModel, FragmentChatBinding>() {

    lateinit var chatAdapter:ChatAdapter
    lateinit var list: MutableList<BeanDataList>
    override fun initLayout(): Int {
        return R.layout.fragment_chat
    }

    override fun initView(savedInstanceState:Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(ChatViewModel::class.java)
        list= mutableListOf()
        chatAdapter =
            ChatAdapter(requireContext(), R.layout.chat_item,
                Identification.CHAT)
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
        viewModel.noHttpRx= NoHttpRx(this)
        viewModel.chatList("1")
    }

    override fun setListener() {
        super.setListener()
        chat_recycler.setPullRefreshEnabled(false)
        chat_recycler.setLoadMoreEnabled(false)
        mLRecycleViewAdapter.setNbOnItemClickListener { view, position ->
            var bundle=Bundle()
            bundle.putString("chatUser",list[position].chatUser)
            bundle.putString("category",list[position].category)
            viewModel.toChatDetails(view,bundle)
        }
        maillist_iv.clickNoRepeat {
            viewModel.toMailList(it)
        }
        add_friend_iv.clickNoRepeat {
            viewModel.toCreateChat(it)
        }
    }

    override fun toData(flag: String?, `object`: String?) {
        super.toData(flag, `object`)
        when (flag) {
            "聊天列表" -> {
                val dataList= Gson ().fromJson(`object`,BeanList::class.java)
                list.clear()
                list.addAll(dataList.data)
                chatAdapter.setDataList(dataList.data)
            }
            else -> {
            }
        }
    }
}
