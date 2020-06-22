package com.example.smartagriculture.fragment

import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.common.*
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.ChatAdapter
import com.example.smartagriculture.app.MyApplication
import com.example.smartagriculture.databinding.FragmentChatDetailsBinding
import com.example.smartagriculture.util.AndroidBug5497Workaround
import com.example.smartagriculture.util.Identification
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.ChatViewModel
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_chat_details.*
import kotlinx.android.synthetic.main.title_item_two.*

/**
 * A simple [Fragment] subclass.
 */
class ChatDetailsFragment : BaseFragment<ChatViewModel, FragmentChatDetailsBinding>() {

    lateinit var chatAdapter: ChatAdapter
    var dataList= mutableListOf<String>()
    private lateinit var linearLayoutManager:LinearLayoutManager
    override fun initLayout(): Int {
        return R.layout.fragment_chat_details
    }

    override fun initView(view: View) {
        viewModel = ViewModelProvider(requireActivity()).get(ChatViewModel::class.java)
        textView.text="聊天详情"
    }

    override fun initData() {
        chatAdapter =
            ChatAdapter(requireContext(), R.layout.chatdetails_item, Identification.CHATDETAILS)
        mLRecycleViewAdapter = LRecyclerViewAdapter(chatAdapter)
        dataList = mutableListOf<String>("一直小可爱", "两只小可爱")
        chatAdapter.setDataList(dataList as Collection<Any?>?)
        chatdetails_recycler.adapter = mLRecycleViewAdapter
        linearLayoutManager=LinearLayoutManager(context)
            //弹出软键盘recyclerview上移
        linearLayoutManager.stackFromEnd =true
        linearLayoutManager.scrollToPositionWithOffset(dataList.size-1,0)
        chatdetails_recycler.layoutManager = linearLayoutManager
    }

    override fun onResume() {
        super.onResume()
        initData()
        editText_msg.isFocusable=true
        editText_msg.isFocusableInTouchMode=true
    }
    override fun setListener() {
        super.setListener()
        chatdetails_recycler.setPullRefreshEnabled(false)
//        setSoftKeyBoardListener()
        send.setOnClickListener {
            if (null == editText_msg.text || TextUtils.isEmpty(editText_msg.text)) {
                ToastUtil("聊天内容不能为空")
                return@setOnClickListener
            }
            editText_msg.text = null
        }
        back.setOnClickListener {
            hideSoftKeyboard(requireActivity())
            nav().navigateUp()
        }
    }
    /**
     * 添加软键盘的监听
     */
    private fun setSoftKeyBoardListener() {
        //监听软件盘是否弹起
            SoftKeyBoardListener.setListener(activity,  object :
                SoftKeyBoardListener.OnSoftKeyBoardChangeListener {

                override fun keyBoardShow(height: Int) {
                    if (isResumed) {
                        linearLayoutManager=LinearLayoutManager(BaseApplication.context)
                        //弹出软键盘recyclerview上移
                        linearLayoutManager.stackFromEnd =true
                        linearLayoutManager.scrollToPositionWithOffset(dataList.size-1, 0)
                        chatdetails_recycler.layoutManager = linearLayoutManager

                    }

                }

                override fun keyBoardHide(height: Int) {
                }
            })


    }

    override fun onPause() {
        super.onPause()
        editText_msg.isFocusable=false
        editText_msg.isFocusableInTouchMode=false
    }

}
