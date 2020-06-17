package com.example.smartagriculture.viewmodel

import android.app.Application
import android.view.View
import androidx.navigation.Navigation
import com.example.common.BaseViewModel
import com.example.smartagriculture.R
import com.example.smartagriculture.util.nav

class DataViewModel(application: Application) : BaseViewModel(application) {


    fun toAddProduct(view: View) {
        nav(view).navigate(R.id.action_productFragment_to_addProductFragment)
    }
}