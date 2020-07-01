package com.example.housekeeper.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.common.data.BaseField
import com.example.library.R
@Route(path = BaseField.HOUSEKEEPER_PATH)
class HousekeeperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_housekeeper)
    }
}
