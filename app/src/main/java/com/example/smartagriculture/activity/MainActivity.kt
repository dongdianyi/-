package com.example.smartagriculture.activity

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.common.base.BaseActivity
import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.ActivityMainBinding
import com.example.smartagriculture.util.AndroidBug5497Workaround
import com.example.smartagriculture.viewmodel.MainViewModel

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
