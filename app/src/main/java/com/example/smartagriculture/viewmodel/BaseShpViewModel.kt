package com.example.smartagriculture.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.example.common.base.BaseViewModel

open class BaseShpViewModel(
    application: Application,
    private var savedStateHandle: SavedStateHandle
) : BaseViewModel(application) {

    var loginName =
        getApplication<Application>().resources.getString(com.example.common.R.string.LOGIN_NAME)
    var loginPhone =
        getApplication<Application>().resources.getString(com.example.common.R.string.LOGIN_PHONE)
    var loginPwd =
        getApplication<Application>().resources.getString(com.example.common.R.string.LOGIN_PWD)
    var userId =
        getApplication<Application>().resources.getString(com.example.common.R.string.USERID)
    var companyId =
        getApplication<Application>().resources.getString(com.example.common.R.string.COMPANYID)
    var shpName =
        getApplication<Application>().resources.getString(com.example.common.R.string.SHP_NAME)

    fun init(): Unit {
        if (!savedStateHandle.contains(userId)) {
            load()
        }
    }

    fun load() {
        val sharedPreferences =
            getApplication<Application>().getSharedPreferences(
                shpName,
                Context.MODE_PRIVATE
            )
        savedStateHandle.set(loginName, sharedPreferences.getString(loginName, ""))
        savedStateHandle.set(loginPwd, sharedPreferences.getString(loginPwd, ""))
        savedStateHandle.set(loginPhone, sharedPreferences.getString(loginPhone, ""))
        savedStateHandle.set(userId, sharedPreferences.getString(userId, ""))
        savedStateHandle.set(companyId, sharedPreferences.getString(companyId, ""))

    }

    fun getName(): LiveData<String> {
        return savedStateHandle.getLiveData(loginName)
    }

    fun getPwd(): LiveData<String> {
        return savedStateHandle.getLiveData(loginPwd)
    }

    fun getUserId(): LiveData<String> {
        return savedStateHandle.getLiveData(userId)
    }

    fun save(name: String?, pwd: String,  phone: String?, userid: String, companyid: String) {
        savedStateHandle.set(loginName, name)
        savedStateHandle.set(loginPwd, pwd)
        savedStateHandle.set(loginPhone, phone)
        savedStateHandle.set(userId, userid)
        savedStateHandle.set(companyId, companyid)
        val sharedPreferences =
            getApplication<Application>().getSharedPreferences(
                shpName,
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.putString(loginName, name)
        editor.putString(loginPwd, pwd)
        editor.putString(loginPhone, phone)
        editor.putString(userId, userid)
        editor.putString(companyId, companyid)
        editor.apply()
    }


}