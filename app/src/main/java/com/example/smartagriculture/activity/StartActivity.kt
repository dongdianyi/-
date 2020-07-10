package com.example.smartagriculture.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.text.TextUtils
import androidx.lifecycle.ViewModelProvider
import com.example.common.base.BaseActivity
import com.example.common.LogUtil
import com.example.common.clickNoRepeat
import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.ActivityStartBinding
import com.example.common.data.Identification
import com.example.smartagriculture.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_start.*
@SuppressLint("SetTextI18n")
class StartActivity : BaseActivity<MainViewModel, ActivityStartBinding>() {

    private lateinit var mHandlerMessage: Handler
    private var count = 3
    private var userId:String?=null
     init {
        mHandlerMessage =
            Handler(Handler.Callback { msg ->
                if (msg.what == Identification.WHATNUM) {
                    LogUtil("时间倒数", count.toString() + "")
                    if (count > 0) {
                        textView34.text = "$count s"
                        count--
                        mHandlerMessage.sendEmptyMessageDelayed(Identification.WHATNUM, 1000)
                    } else {
                        if (TextUtils.isEmpty(userId)) {
                            startActivity(Intent(this@StartActivity, LoginActivity::class.java))
                        }else{
                            startActivity(Intent(this@StartActivity, MainActivity::class.java))
                        }
                        finish()
                    }
                }
                false
            })
    }

    override fun initLayout(): Int {
        return R.layout.activity_start
    }

    override fun initView() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        userId= viewModel.getUserId().value
        mHandlerMessage.sendEmptyMessageDelayed(Identification.WHATNUM, 1000)
    }

    override fun initData() {
        textView34.isEnabled=true
        textView34.clickNoRepeat {
            textView34.isEnabled=false
            mHandlerMessage.removeCallbacksAndMessages(null)
            if (TextUtils.isEmpty(userId)) {
                startActivity(Intent(this@StartActivity, LoginActivity::class.java))
            }else{
                startActivity(Intent(this@StartActivity, MainActivity::class.java))
            }
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandlerMessage.removeCallbacksAndMessages(null)
    }
}
