package com.example.smartagriculture.viewmodel

import android.app.Application
import android.view.View
import com.example.common.base.BaseViewModel
import com.example.smartagriculture.R
import com.example.smartagriculture.util.nav

class MineViewModel(application: Application) : BaseViewModel(application) {

    fun toRevisedInformation(view: View): Unit {
        nav(view).navigate(R.id.action_mainFragment_to_revisedInformationFragment)
    }
    fun toPhone(view: View): Unit {
        nav(view).navigate(R.id.action_mainFragment_to_phoneFragment)
    }
    fun toUpdatePwd(view: View): Unit {
        nav(view).navigate(R.id.action_mainFragment_to_updatePwdFragment)
    }

}