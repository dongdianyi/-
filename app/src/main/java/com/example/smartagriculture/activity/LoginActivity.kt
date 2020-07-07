package com.example.smartagriculture.activity

import android.Manifest
import android.content.Intent
import android.text.TextUtils
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.example.common.*
import com.example.common.base.BaseActivity
import com.example.common.bean.Bean
import com.example.common.model.NoHttpRx
import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.ActivityLoginBinding
import com.example.smartagriculture.viewmodel.MainViewModel
import com.google.gson.Gson
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<MainViewModel, ActivityLoginBinding>() {

    override fun initLayout(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        viewModel = ViewModelProvider(
            this,
            SavedStateViewModelFactory(application, this)
        ).get(MainViewModel::class.java)
        dataBinding.data = viewModel
        dataBinding.lifecycleOwner = this
        viewModel.init()
    }

    override fun initData() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
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
                LogUtil("永久拒绝的权限", deniedList)

                showForwardToSettingsDialog(deniedList, "您需要去应用程序设置当中手动开启权限", "我已明白")
            }
            .request { allGranted, grantedList, deniedList ->
                if (grantedList.contains(Manifest.permission.CAMERA)) {

                    ToastUtil("摄像头申请的权限已通过")

                }
                if (allGranted) {
                    textView126.clickNoRepeat {
                        if (TextUtils.isEmpty(editText4.text)) {
                            ToastUtil("请输入用户名/手机号")
                            return@clickNoRepeat
                        }
                        if (TextUtils.isEmpty(editText5.text)) {
                            ToastUtil("请输入密码")
                            return@clickNoRepeat
                        }
                        viewModel.noHttpRx = NoHttpRx(this)
                        viewModel.login(
                            editText4.text.toString(),
                            MD5Util.md5(Base64Utils.encodeToString(editText5.text.toString()))
                        )
                    }

                } else {
                    ToastUtil("您拒绝了如下权限：$deniedList")
                }


            }

    }

    override fun toData(flag: String?, `object`: String?) {
        super.toData(flag, `object`)
        when (flag) {
            "登录" -> {
                val bean= Gson().fromJson(`object`, Bean::class.java)
                viewModel.save(editText4.text.toString(), editText5.text.toString(),bean.data.usermsg.telephone,bean.data.userid,bean.data.companyid)
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
            else -> {
            }
        }
    }

    override fun fail(isNetWork: Boolean, flag: String?, t: Throwable?) {
        super.fail(isNetWork, flag, t)
        if (t != null) {
            ToastUtil(t.message)
        }
    }
}
