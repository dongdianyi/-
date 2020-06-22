package com.example.smartagriculture.viewmodel

import android.app.Application
import android.view.View
import androidx.navigation.Navigation
import com.example.common.BaseViewModel
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

}