package com.example.smartagriculture.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.ToastUtil
import com.example.common.base.BaseFragment
import com.example.common.bean.Bean
import com.example.common.bean.BeanDataList
import com.example.common.bean.BeanList
import com.example.common.clickNoRepeat
import com.example.common.hideSoftKeyboard
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.ChatAdapter
import com.example.smartagriculture.databinding.FragmentCreateChatBinding
import com.example.common.data.Identification
import com.example.common.getPop
import com.example.common.model.NoHttpRx
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.ChatViewModel
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.chat_dialog.view.*
import kotlinx.android.synthetic.main.fragment_create_chat.*

class CreateChatFragment : BaseFragment<ChatViewModel, FragmentCreateChatBinding>() {

    private lateinit var chatAdapter: ChatAdapter
    private lateinit var datalist:MutableList<BeanDataList>
    private lateinit var rootView:View
    private lateinit var array:MutableList<Map<String,String>>
    lateinit var bean:Bean
    override fun initLayout(): Int {
        return R.layout.fragment_create_chat
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(ChatViewModel::class.java)
        datalist= mutableListOf()
        rootView =
            LayoutInflater.from(activity).inflate(R.layout.chat_dialog, null)
        array = mutableListOf()
        chatAdapter =
            ChatAdapter(requireContext(), R.layout.chat_item, Identification.ADDCHAT)
        mLRecycleViewAdapter = LRecyclerViewAdapter(chatAdapter)
        create_chat.adapter = mLRecycleViewAdapter

        val divider: DividerDecoration = DividerDecoration.Builder(requireContext())
            .setHeight(R.dimen.mm_2)
            .setColorResource(R.color.bg)
            .build()
        create_chat.addItemDecoration(divider)
        create_chat.layoutManager = LinearLayoutManager(requireContext())
        viewModel.dialogCircle =
            getPop(activity, rootView, 2, 3, Gravity.CENTER, 0, false)
    }

    override fun lazyLoadData() {
        viewModel.noHttpRx= NoHttpRx(this)
        viewModel.getMailList("1244451714554269696","")

    }

    @SuppressLint("InflateParams")
    override fun setListener() {
        create_chat.setPullRefreshEnabled(false)
        textView46.clickNoRepeat {
            var i=0
            datalist.forEach {
                if (it.isChoose) {
                    val map= hashMapOf<String,String>()
                    map["userid"] = it.userId
                    array.add(i, map)
                    i++
                }
            }
            viewModel.createChat("1244451714554269696", array)
        }
        rootView.textView136.setOnClickListener {

            viewModel.upDateGroup(bean.data.groupId)
        }
        rootView.imageView48.setOnClickListener {
            if (viewModel.dialogCircle.isShowing) {
                viewModel.dialogCircle.dismiss()
            }
        }

        mLRecycleViewAdapter.setOnItemClickListener { view, position ->
            chatAdapter.multipleChoose(position)
        }
        back.setOnClickListener {
            hideSoftKeyboard(requireActivity())
            nav().navigateUp()
        }
    }

    override fun toData(flag: String?, `object`: String?) {
        super.toData(flag, `object`)
        when (flag) {
            "通讯录" -> {
                var beanList= Gson().fromJson(`object`, BeanList::class.java)
                datalist.addAll(beanList.data)
                chatAdapter.setDataList(beanList.data)
            }
            "创建群"->{
                bean=Gson().fromJson(`object`,Bean::class.java)
                viewModel.dialogCircle.show()
                rootView.editText6.setText(bean.data.groupName)
            }
            "修改群"->{
                viewModel.dialogCircle.dismiss()
                nav().navigate(R.id.action_createChatFragment_to_chatDetailsFragment)
            }
            else -> {
            }
        }

    }
}
