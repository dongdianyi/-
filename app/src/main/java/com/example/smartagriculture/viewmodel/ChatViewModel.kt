package com.example.smartagriculture.viewmodel

import android.app.Application
import android.view.View
import com.example.common.base.BaseViewModel
import com.example.common.data.BaseUrl
import com.example.common.data.CommitParam
import com.example.smartagriculture.R
import com.example.smartagriculture.util.nav

class ChatViewModel(application: Application) : BaseViewModel(application) {

    fun toChatDetails(view:View) {
        nav(view).navigate(R.id.action_mainFragment_to_chatDetailsFragment)
    }
    fun MailtoChatDetails(view:View) {
        nav(view).navigate(R.id.action_mailListFragment_to_chatDetailsFragment)
    }

    fun toMailList(view:View) {
        nav(view).navigate(R.id.action_mainFragment_to_mailListFragment)

    }
    fun toCreateChat(view:View) {
        nav(view).navigate(R.id.action_mainFragment_to_createChatFragment)

    }

    fun getMailList(userId:String,name:String): Unit {
        var commitParam = CommitParam()
        commitParam.userId = userId
        commitParam.name = name
        var map = hashMapOf<String, String>()
        noHttpRx.postHttpJson(
            map,
            "通讯录",
            BaseUrl.BASE_URL3 + BaseUrl.MAINLIST,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }
    fun getMailListGroup(userId:String,name:String): Unit {
        var commitParam = CommitParam()
        commitParam.userId = userId
        commitParam.name = name
        var map = hashMapOf<String, String>()
        noHttpRx.postHttpJson(
            map,
            "群聊",
            BaseUrl.BASE_URL3 + BaseUrl.MAINLIST_GROUP,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }
    fun chatList(userId:String,type:String): Unit {
        var commitParam = CommitParam()
        commitParam.userId = userId
        commitParam.type = type
        var map = hashMapOf<String, String>()
        noHttpRx.postHttpJson(
            map,
            "聊天列表",
            BaseUrl.BASE_URL3 + BaseUrl.CHATRECORD,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }

}