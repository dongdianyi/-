package com.example.smartagriculture.activity

import android.Manifest
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.common.BaseActivity
import com.example.common.ToastUtil
import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.ActivityLoginBinding
import com.example.smartagriculture.viewmodel.MainViewModel
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<MainViewModel,ActivityLoginBinding>(){

    override fun initLayout(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//        dataBinding.data = viewModel
//        dataBinding.lifecycleOwner = this

    }

    override fun initData() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            .explainReasonBeforeRequest()
                .onExplainRequestReason { deniedList ->
                    showRequestReasonDialog(deniedList, "即将申请的权限是程序必须依赖的权限", "我已明白")
//                    showRequestReasonDialog(filteredList, "摄像头权限是程序必须依赖的权限", "我已明白", "取消")
                }
//            .onExplainRequestReason { deniedList, beforeRequest ->
//                val filteredList = deniedList.filter {
//                    it == Manifest.permission.CAMERA
//                }
//                LogUtil("deniedList", deniedList)
//                LogUtil("filteredList", filteredList)
//                if (beforeRequest) {
//                    //在请求权限之前的逻辑
//                    if (filteredList.isEmpty()) {
//                        showRequestReasonDialog(filteredList, "为了保证程序正常工作，请您同意以下权限申请", "我已明白")
//                    } else {
//                        showRequestReasonDialog(deniedList, "为了保证程序正常工作，请您同意以下权限申请", "我已明白")
//                    }
//                } else {
//                    //请求之后的逻辑
//
////                    showRequestReasonDialog(deniedList, "即将申请的权限是程序必须依赖的权限", "我已明白")
//                    showRequestReasonDialog(filteredList, "摄像头权限是程序必须依赖的权限", "我已明白", "取消")
//                }
//            }
            .onForwardToSettings { deniedList ->
                //监听那些被用户永久拒绝的权限
                showForwardToSettingsDialog(deniedList, "您需要去应用程序设置当中手动开启权限", "我已明白")
            }
            .request { allGranted, grantedList, deniedList ->
                if (grantedList.contains(Manifest.permission.CAMERA)) {

                    ToastUtil("摄像头申请的权限已通过")

                }
                if (allGranted) {

                    Toast.makeText(this, "所有申请的权限都已通过", Toast.LENGTH_SHORT).show()
                    textView126.setOnClickListener {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }

                } else {

                    Toast.makeText(this, "您拒绝了如下权限：$deniedList", Toast.LENGTH_SHORT).show()

                }


            }

    }
}
