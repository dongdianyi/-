package com.example.smartagriculture.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.BaseFragment
import com.example.common.setNbOnItemClickListener

import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.ChatAdapter
import com.example.smartagriculture.adapter.ParkAdapter
import com.example.smartagriculture.databinding.FragmentChatBinding
import com.example.smartagriculture.util.Identification
import com.example.smartagriculture.viewmodel.ChatViewModel
import com.example.smartagriculture.viewmodel.MainViewModel
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class ChatFragment : BaseFragment<ChatViewModel,FragmentChatBinding>() {

    lateinit var chatAdapter:ChatAdapter
    override fun initLayout(): Int {
        return R.layout.fragment_chat
    }

    override fun initView(view: View) {
        viewModel = ViewModelProvider(requireActivity()).get(ChatViewModel::class.java)
    }

    override fun initData() {
        chatAdapter =
            ChatAdapter(requireContext(), R.layout.chat_item,Identification.CHAT)
        mLRecycleViewAdapter = LRecyclerViewAdapter(chatAdapter)
        val dataList = listOf<String>("一直小可爱", "两只小可爱", "章丘大鱼合作社","一直小可爱", "两只小可爱", "章丘大鱼合作社","一直小可爱", "两只小可爱", "章丘大鱼合作社")
        chatAdapter.setDataList(dataList)
        chat_recycler.adapter = mLRecycleViewAdapter

        val divider: DividerDecoration = DividerDecoration.Builder(requireContext())
            .setHeight(R.dimen.dp_2)
            .setColorResource(R.color.bg)
            .build()
        chat_recycler.addItemDecoration(divider)
        chat_recycler.layoutManager = LinearLayoutManager(requireContext())

    }

    override fun setListener() {
        super.setListener()
        chat_recycler.setPullRefreshEnabled(false)
        chat_recycler.setLoadMoreEnabled(false)
        mLRecycleViewAdapter.setNbOnItemClickListener { view, position ->
            viewModel.toChatDetails(view)
        }
        maillist_iv.setOnClickListener {
            viewModel.toMailList(it)
        }
        add_friend_iv.setOnClickListener {
            viewModel.toCreateChat(it)
        }
    }
}
