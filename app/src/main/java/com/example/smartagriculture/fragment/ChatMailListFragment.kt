package com.example.smartagriculture.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.base.BaseFragment
import com.example.common.bean.BeanDataList
import com.example.common.bean.BeanList
import com.example.common.setNbOnItemClickListener
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.ChatAdapter
import com.example.smartagriculture.databinding.FragmentChatMailListBinding
import com.example.common.data.Identification
import com.example.common.data.Identification.Companion.ONE
import com.example.common.data.Identification.Companion.THREE
import com.example.common.model.NoHttpRx
import com.example.smartagriculture.viewmodel.ChatViewModel
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_chat_mail_list.*

/**
 * A simple [Fragment] subclass.
 */
class ChatMailListFragment : BaseFragment<ChatViewModel, FragmentChatMailListBinding>() {

    lateinit var title: String
    lateinit var chatAdapter: ChatAdapter
    lateinit var list: MutableList<BeanDataList>
    lateinit var category:String

    companion object {
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

    override fun initView(savedInstanceState:Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(ChatViewModel::class.java)
        title = arguments?.getString("title").toString()
        list= mutableListOf()

        chatAdapter =
            ChatAdapter(requireContext(), R.layout.chat_item, Identification.CHATMAILLIST)
        mLRecycleViewAdapter = LRecyclerViewAdapter(chatAdapter)
        chatmaillist_recycler.adapter = mLRecycleViewAdapter

        val divider: DividerDecoration = DividerDecoration.Builder(requireContext())
            .setHeight(R.dimen.mm_2)
            .setColorResource(R.color.bg)
            .build()
        chatmaillist_recycler.addItemDecoration(divider)
        chatmaillist_recycler.layoutManager = LinearLayoutManager(requireContext())

    }
    override fun lazyLoadData() {
        viewModel.noHttpRx= NoHttpRx(this)

        if (getString(R.string.maillist) ==title) {
            category=ONE
            viewModel.getMailList("")
            search_tv.setHint(R.string.search_maillist)
        } else {
            category=THREE
            viewModel.getMailListGroup("")
            search_tv.setHint(R.string.search_chat)
        }

    }

    override fun setListener() {
        chatmaillist_recycler.setPullRefreshEnabled(false)
        mLRecycleViewAdapter.setNbOnItemClickListener { view, position ->
            var bundle=Bundle()
            bundle.putString("chatUser",list[position].chatUser)
            bundle.putString("category",category)
            viewModel.MailtoChatDetails(view,bundle)
        }
    }
    override fun toData(flag: String?, `object`: String?) {
        super.toData(flag, `object`)
        when (flag) {
            "通讯录" -> {
                var beanList= Gson().fromJson(`object`, BeanList::class.java)
                list.clear()
                list.addAll(beanList.data)
                chatAdapter.setDataList(beanList.data)
            }
            "群聊" ->{
                var beanList= Gson().fromJson(`object`, BeanList::class.java)
                list.clear()
                list.addAll(beanList.data)
                chatAdapter.setDataList(beanList.data)
            }
            else -> {
            }
        }

    }

}
