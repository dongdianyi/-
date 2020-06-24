package com.example.smartagriculture.activity

import android.Manifest
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.BaseActivity
import com.example.common.ToastUtil
import com.example.common.hideSoftKeyboard
import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.ActivityMainBinding
import com.example.smartagriculture.util.AndroidBug5497Workaround
import com.example.smartagriculture.viewmodel.MainViewModel
import com.permissionx.guolindev.PermissionX

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun initLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        dataBinding.data = viewModel
        dataBinding.lifecycleOwner = this

    }

    override fun initData() {
        AndroidBug5497Workaround.assistActivity(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.main_navigation).navigateUp()
    }
}
