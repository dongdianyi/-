package com.example.smartagriculture.fragment

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.BaseFragment
import com.example.common.LogUtil
import com.example.common.hideSoftKeyboard
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.ChatAdapter
import com.example.smartagriculture.bean.CreatChat
import com.example.smartagriculture.databinding.FragmentCreateChatBinding
import com.example.smartagriculture.util.Identification
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.ChatViewModel
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_create_chat.*

class CreateChatFragment : BaseFragment<ChatViewModel, FragmentCreateChatBinding>() {

    lateinit var chatAdapter: ChatAdapter
    lateinit var creatChat: CreatChat
    var datalist = mutableListOf<CreatChat>()
    override fun initLayout(): Int {
        return R.layout.fragment_create_chat
    }

    override fun initView(view: View) {
        viewModel = ViewModelProvider(requireActivity()).get(ChatViewModel::class.java)
        chatAdapter =
            ChatAdapter(requireContext(), R.layout.chat_item, Identification.ADDCHAT)
        mLRecycleViewAdapter = LRecyclerViewAdapter(chatAdapter)
        for (i in 0..10) {
            creatChat = CreatChat()
            creatChat.isChoose = false
            creatChat.message = "$i 只小可爱"
            datalist.add(i,creatChat)
        }
        chatAdapter.setDataList(datalist as Collection<Any?>?)
        create_chat.adapter = mLRecycleViewAdapter

        val divider: DividerDecoration = DividerDecoration.Builder(requireContext())
            .setHeight(R.dimen.dp_2)
            .setColorResource(R.color.bg)
            .build()
        create_chat.addItemDecoration(divider)
        create_chat.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun initData() {
    }

    override fun setListener() {
        create_chat.setPullRefreshEnabled(false)
        mLRecycleViewAdapter.setOnItemClickListener { view, position ->
            chatAdapter.multipleChoose(position)
        }
        back.setOnClickListener {
            hideSoftKeyboard(requireActivity())
            nav().navigateUp()
        }
    }
}
