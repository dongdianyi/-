package com.example.smartagriculture.viewmodel

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.lifecycle.SavedStateHandle
import com.example.common.base.BaseViewModel
import com.example.common.data.BaseUrl
import com.example.common.data.CommitParam
import com.example.smartagriculture.R
import com.example.smartagriculture.util.nav
import java.util.*

class ChatViewModel(
    application: Application, savedStateHandle: SavedStateHandle
) : BaseShpViewModel(application, savedStateHandle) {

    fun toChatDetails(view: View) {
        nav(view).navigate(R.id.action_mainFragment_to_chatDetailsFragment)
    }

    fun MailtoChatDetails(view: View) {
        nav(view).navigate(R.id.action_mailListFragment_to_chatDetailsFragment)
    }

    fun toMailList(view: View) {
        nav(view).navigate(R.id.action_mainFragment_to_mailListFragment)

    }

    fun toCreateChat(view: View) {
        nav(view).navigate(R.id.action_mainFragment_to_createChatFragment)

    }

    fun toGroupPerson(view: View, chatUser: String) {
        val bundle = Bundle()
        bundle.putString("chatUser", chatUser)
        nav(view).navigate(R.id.action_chatDetailsFragment_to_groupPersonFragment, bundle)

    }

    fun getMailList(userId: String, name: String): Unit {
        var commitParam = CommitParam()
        commitParam.userId = userId
        commitParam.name = name
        var map = hashMapOf<String, String>()
        map[getApplication<Application>().resources.getString(R.string.token)] =
            getUserId().value.toString()
        noHttpRx.postHttpJson(
            map,
            "通讯录",
            BaseUrl.BASE_URL3 + BaseUrl.MAINLIST,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }

    fun getMailListGroup(userId: String, name: String): Unit {
        var commitParam = CommitParam()
        commitParam.userId = userId
        commitParam.name = name
        var map = hashMapOf<String, String>()
        map[getApplication<Application>().resources.getString(R.string.token)] =
            getUserId().value.toString()

        noHttpRx.postHttpJson(
            map,
            "群聊",
            BaseUrl.BASE_URL3 + BaseUrl.MAINLIST_GROUP,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }

    fun chatList(userId: String, type: String): Unit {
        var commitParam = CommitParam()
        commitParam.userId = userId
        commitParam.type = type
        var map = hashMapOf<String, String>()
        map[getApplication<Application>().resources.getString(R.string.token)] =
            getUserId().value.toString()

        noHttpRx.postHttpJson(
            map,
            "聊天列表",
            BaseUrl.BASE_URL3 + BaseUrl.CHATMESSAGE,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }

    fun chatRecord(userId: String, chatUser: String, category: String, page: String): Unit {
        var commitParam = CommitParam()
        commitParam.userId = userId
        commitParam.chatUser = chatUser
        commitParam.category = category
        commitParam.page = page
        commitParam.pageSize = "10"
        var map = hashMapOf<String, String>()
        map[getApplication<Application>().resources.getString(R.string.token)] =
            getUserId().value.toString()

        noHttpRx.postHttpJson(
            map,
            "聊天记录",
            BaseUrl.BASE_URL3 + BaseUrl.CHATRECORD,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }

    fun createChat(userId: String, userIds: MutableList<Map<String, String>>): Unit {
        var commitParam = CommitParam()
        commitParam.userId = userId
        commitParam.userIds = userIds
        var map = hashMapOf<String, String>()
        map[getApplication<Application>().resources.getString(R.string.token)] =
            getUserId().value.toString()

        noHttpRx.postHttpJson(
            map,
            "创建群",
            BaseUrl.BASE_URL3 + BaseUrl.CREATECHAT,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }

    fun getGroupPerson(groupid: String): Unit {
        var commitParam = CommitParam()
        commitParam.groupid = groupid
        var map = hashMapOf<String, String>()
        map[getApplication<Application>().resources.getString(R.string.token)] =
            getUserId().value.toString()

        noHttpRx.postHttpJson(
            map,
            "群成员",
            BaseUrl.BASE_URL3 + BaseUrl.GROUPPERSON,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }

    fun upDateGroup(id: Int): Unit {
        var commitParam = CommitParam()
        commitParam.id = id
        var map = hashMapOf<String, String>()
        map[getApplication<Application>().resources.getString(R.string.token)] =
            getUserId().value.toString()

        noHttpRx.postHttpJson(
            map,
            "修改群",
            BaseUrl.BASE_URL3 + BaseUrl.UPDATEGROUP,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }

}