package com.example.smartagriculture.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.example.common.R
import com.example.common.base.BaseViewModel
import com.example.common.data.CommitParam

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
    var Cookie =
        getApplication<Application>().resources.getString(R.string.COOKIE)
    var mType =
        getApplication<Application>().resources.getString(R.string.TYPE)
    var isFirst =
        getApplication<Application>().resources.getString(R.string.ISFIRST)
    lateinit var map: Map<String,String>
    init {
        if (!savedStateHandle.contains(userId)) {
            load()
        }
        setHeader()
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
        savedStateHandle.set(mType, sharedPreferences.getInt(mType, 2))
        savedStateHandle.set(isFirst, sharedPreferences.getBoolean(isFirst, true))

    }

    fun getName(): LiveData<String> {
        return savedStateHandle.getLiveData(loginName)
    }
    fun getType(): LiveData<Int> {
        return savedStateHandle.getLiveData(mType)
    }

    fun getPwd(): LiveData<String> {
        return savedStateHandle.getLiveData(loginPwd)
    }

    fun getUserId(): LiveData<String> {
        return savedStateHandle.getLiveData(userId)
    }
    fun getIsFirst(): LiveData<Boolean> {
        return savedStateHandle.getLiveData(isFirst)
    }
    fun getCookie(): LiveData<String> {
        return savedStateHandle.getLiveData(Cookie)
    }
    fun getCompanyId(): LiveData<String> {
        return savedStateHandle.getLiveData(companyId)
    }
    fun saveType(type: Int): Unit {
        savedStateHandle.set(mType, type)
        savedStateHandle.set(isFirst, false)
        val sharedPreferences =
            getApplication<Application>().getSharedPreferences(
                shpName,
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.putInt(mType, type)
        editor.putBoolean(isFirst, false)
        editor.apply()
    }
    fun save(name: String?, pwd: String,  phone: String?, userid: String, companyid: String,cookie:String) {
        savedStateHandle.set(loginName, name)
        savedStateHandle.set(loginPwd, pwd)
        savedStateHandle.set(loginPhone, phone)
        savedStateHandle.set(userId, userid)
        savedStateHandle.set(companyId, companyid)
        savedStateHandle.set(Cookie, cookie)
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
        editor.putString(Cookie, cookie)
        editor.apply()
    }
    fun setHeader(): Unit {
        map = hashMapOf<String, String>()
        (map as HashMap<String, String>)[getApplication<Application>().resources.getString(R.string.token)] = getUserId().value.toString()
        (map as HashMap<String, String>)[getApplication<Application>().resources.getString(R.string.COOKIE)] = getCookie().value.toString()
    }

}