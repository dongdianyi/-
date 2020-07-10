package com.example.smartagriculture.activity

import android.view.KeyEvent
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.common.ToastUtil
import com.example.common.base.BaseActivity
import com.example.common.bean.Bean
import com.example.common.model.NoHttpRx
import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.ActivityMainBinding
import com.example.smartagriculture.util.AndroidBug5497Workaround
import com.example.smartagriculture.viewmodel.MainViewModel
import com.google.gson.Gson

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    var tempTime: Long = 2000
    var firstTime: Long = 0
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
        //系统经纬度
//        viewModel.getLocation(this)
        viewModel.noHttpRx = NoHttpRx(this)
        viewModel.getAppRole()

    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.main_navigation).navigateUp()
    }

    override fun toData(flag: String?, `object`: String?) {
        super.toData(flag, `object`)
        when (flag) {
            "考勤权限" -> {
                val bean = Gson().fromJson(`object`, Bean::class.java)
                viewModel.saveType(bean.data.type)

            }
            else -> {
            }
        }
    }

}
