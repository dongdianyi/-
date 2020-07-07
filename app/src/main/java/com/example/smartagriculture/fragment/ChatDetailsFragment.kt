package com.example.smartagriculture.fragment

import android.os.Bundle
import android.os.Message
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.*
import com.example.common.base.BaseFragment
import com.example.common.data.BaseUrl
import com.example.common.data.Identification
import com.example.common.model.NoHttpRx
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.ChatAdapter
import com.example.smartagriculture.databinding.FragmentChatDetailsBinding
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.ChatViewModel
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_chat_details.*
import kotlinx.android.synthetic.main.title_item_two.*
import java.net.URI

/**
 * A simple [Fragment] subclass.
 */
class ChatDetailsFragment : BaseFragment<ChatViewModel, FragmentChatDetailsBinding>() {

    lateinit var chatAdapter: ChatAdapter
    var dataList = mutableListOf<String>()
    lateinit var chatUser:String
    lateinit var category:String
    private lateinit var linearLayoutManager: LinearLayoutManager

    //webSocket监测下发信息
    private var client: JWebSocketClient? = null

    override fun initLayout(): Int {
        return R.layout.fragment_chat_details
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(ChatViewModel::class.java)
        textView.text = "聊天详情"
        chatUser=arguments?.getString("chatUser").toString()
        category= arguments?.getString("category").toString()
        if (Identification.THREE==category) {
            imageView49.visibility=View.VISIBLE
        }else{
            imageView49.visibility=View.GONE
        }
        chatAdapter =
            ChatAdapter(requireContext(), R.layout.chatdetails_item, Identification.CHATDETAILS)
        mLRecycleViewAdapter = LRecyclerViewAdapter(chatAdapter)
        chatdetails_recycler.adapter = mLRecycleViewAdapter
        linearLayoutManager = LinearLayoutManager(context)
//        弹出软键盘recyclerview上移
        linearLayoutManager.reverseLayout=true
        linearLayoutManager.stackFromEnd = true

        linearLayoutManager.scrollToPositionWithOffset(0, 0)

        chatdetails_recycler.layoutManager = linearLayoutManager
    }

    override fun lazyLoadData() {
        //初始化webSocket
        initSocketClient()
        viewModel.noHttpRx= NoHttpRx(this)
        viewModel.chatRecord("1244451714554269696",chatUser,category,"1")

    }

    override fun onResume() {
        super.onResume()
//        linearLayoutManager.scrollToPositionWithOffset(0, 0)
        mLRecycleViewAdapter.notifyDataSetChanged()
        editText_msg.isFocusable = true
        editText_msg.isFocusableInTouchMode = true
    }

    override fun setListener() {
        super.setListener()
        chatdetails_recycler.setPullRefreshEnabled(false)
        chatdetails_recycler.setLoadMoreEnabled(false)
        setSoftKeyBoardListener()
        imageView49.setOnClickListener {
            viewModel.toGroupPerson(it,chatUser)
        }
        send.setOnClickListener {
            if (null == editText_msg.text || TextUtils.isEmpty(editText_msg.text)) {
                ToastUtil("聊天内容不能为空")
                return@setOnClickListener
            }
            editText_msg.text = null
        }
        back.setOnClickListener {
            hideSoftKeyboard(requireActivity())
            nav().navigate(R.id.action_chatDetailsFragment_to_mainFragment)
        }
    }

    /**
     * 添加软键盘的监听
     */
    private fun setSoftKeyBoardListener() {
        //监听软件盘是否弹起
        SoftKeyBoardListener.setListener(activity, object :
            SoftKeyBoardListener.OnSoftKeyBoardChangeListener {

            override fun keyBoardShow(height: Int) {
                if (chatdetails_recycler!=null) {
                    linearLayoutManager.scrollToPositionWithOffset(0, 0)
                    chatdetails_recycler.layoutManager = linearLayoutManager
                }

            }

            override fun keyBoardHide(height: Int) {
                if (chatdetails_recycler != null) {
                    linearLayoutManager.scrollToPositionWithOffset(0, 0)
                    chatdetails_recycler.layoutManager = linearLayoutManager
                }
            }
        })


    }

    override fun onPause() {
        super.onPause()
        editText_msg.isFocusable = false
        editText_msg.isFocusableInTouchMode = false
    }

    override fun toData(flag: String?, `object`: String?) {
        super.toData(flag, `object`)
        when (flag) {
            "聊天记录" -> {

            }
            else -> {
            }
        }
    }

    fun initSocketClient() {
//        val uri = URI.create(BaseUrl.CHATSOCKET+"1/"+chatUser+)
//        client = object : JWebSocketClient(uri) {
//            override fun onMessage(message: String?) {
//                //message就是接收到的消息
//                LogUtil("socket接收到的消息", message)
//                val message1 = Message()
//                message1.what = whatNum
//                message1.obj = message
//                mHandlerMessage.sendMessage(message1)
//            }
//        }
//        object : Thread() {
//            override fun run() {
//                try {
//                    //connectBlocking多出一个等待操作，会先连接再发送，否则未连接发送会报错
//                    client.connectBlocking()
//                } catch (e: InterruptedException) {
//                    e.printStackTrace()
//                }
//            }
//        }.start()
//        mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE) //开启心跳检测
    }
}
