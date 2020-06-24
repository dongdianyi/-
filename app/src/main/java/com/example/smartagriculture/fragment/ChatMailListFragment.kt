package com.example.smartagriculture.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.BaseFragment
import com.example.common.setNbOnItemClickListener
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.ChatAdapter
import com.example.smartagriculture.databinding.FragmentChatMailListBinding
import com.example.smartagriculture.util.Identification
import com.example.smartagriculture.viewmodel.ChatViewModel
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_chat_mail_list.*

/**
 * A simple [Fragment] subclass.
 */
class ChatMailListFragment : BaseFragment<ChatViewModel,FragmentChatMailListBinding>() {



    companion object{
        fun newInstance(title: String?, flag: Int): ChatMailListFragment? {
            val fragment = ChatMailListFragment()
            val bundle = Bundle()
            bundle.putString("title", title)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initLayout(): Int {
        return R.layout.fragment_chat_mail_list
    }

    override fun initView(view: View) {
    }

    override fun initData() {

        viewModel = ViewModelProvider(requireActivity()).get(ChatViewModel::class.java)
        val chatAdapter =
            ChatAdapter(requireContext(),R.layout.chat_item, Identification.CHATMAILLIST)
        mLRecycleViewAdapter = LRecyclerViewAdapter(chatAdapter)
        val dataList = listOf<String>("一个小可爱", "两个小可爱", "章丘大鱼合作社")
        chatAdapter.setDataList(dataList)
        chatmaillist_recycler.adapter = mLRecycleViewAdapter

        val divider: DividerDecoration = DividerDecoration.Builder(requireContext())
            .setHeight(R.dimen.mm_2)
            .setColorResource(R.color.bg)
            .build()
        chatmaillist_recycler.addItemDecoration(divider)
        chatmaillist_recycler.layoutManager = LinearLayoutManager(requireContext())

    }

    override fun setListener() {
        chatmaillist_recycler.setPullRefreshEnabled(false)
        mLRecycleViewAdapter.setNbOnItemClickListener { view, position ->
            viewModel.MailtoChatDetails(view)
        }
    }

}
