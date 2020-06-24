package com.example.smartagriculture.activity

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.BaseActivity
import com.example.common.hideSoftKeyboard
import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.ActivityMainBinding
import com.example.smartagriculture.util.AndroidBug5497Workaround
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun initLayout(): Int {
        return R.layout.fragment_login
    }

    override fun initView() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        dataBinding.data = viewModel
        dataBinding.lifecycleOwner = this

    }

    override fun initData() {
        AndroidBug5497Workaround.assistActivity(this)
        textView126.setOnClickListener {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))

        }
    }
}
