package com.example.smartagriculture.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.common.ToastUtil
import com.example.common.clickNoRepeat
import com.example.common.data.BaseUrl
import com.example.common.data.CommitParam
import com.example.common.data.Identification.Companion.ONE
import com.example.common.data.Identification.Companion.TWO
import com.example.common.getPop
import com.example.smartagriculture.R
import com.example.smartagriculture.activity.LoginActivity
import com.example.smartagriculture.util.ClearCache
import com.example.smartagriculture.util.nav
import com.jph.takephoto.app.TakePhoto
import com.jph.takephoto.compress.CompressConfig
import com.jph.takephoto.model.CropOptions
import com.jph.takephoto.model.TakePhotoOptions
import java.io.File
import java.util.*

class MineViewModel(
    application: Application, savedStateHandle: SavedStateHandle
) : BaseShpViewModel(application, savedStateHandle) {


    private val sex = MutableLiveData<String>()
    val setSex: LiveData<String>
        get() {
            return sex
        }

    private lateinit var cacheSize: MutableLiveData<String>

    fun toRevisedInformation(view: View): Unit {
        nav(view).navigate(R.id.action_mainFragment_to_revisedInformationFragment)
    }

    fun toPhone(view: View): Unit {
        nav(view).navigate(R.id.action_mainFragment_to_phoneFragment)
    }

    fun toUpdatePwd(view: View): Unit {
        nav(view).navigate(R.id.action_mainFragment_to_updatePwdFragment)
    }

    fun toSetting(view: View): Unit {
        nav(view).navigate(R.id.action_mainFragment_to_settingFragment)
    }

    fun toProblem(view: View): Unit {
        nav(view).navigate(R.id.action_mainFragment_to_problemFragment)
    }

    fun toAbout(view: View): Unit {
        nav(view).navigate(R.id.action_mainFragment_to_aboutFragment)
    }

    fun toFeedBack(view: View): Unit {
        nav(view).navigate(R.id.action_mainFragment_to_feedBackFragment)
    }

    fun toUpdate(view: View): Unit {
        ToastUtil("已是最新版本")
    }

    fun showDialogBase(activity: Activity, title: String, content: String, layout: Int): Unit {
        val rootView =
            View.inflate(activity, layout, null)
        dialogCircle =
            getPop(
                activity,
                rootView,
                2,
                3,
                Gravity.CENTER,
                0,
                false,
                100,
                0
            )
        dialogCircle.show()
        val sureButton = rootView.findViewById<Button>(com.example.common.R.id.sure_button)
        val closeIv = rootView.findViewById<ImageView>(com.example.common.R.id.close_iv)
        val textView129 = rootView.findViewById<TextView>(com.example.common.R.id.textView129)
        val textView130 = rootView.findViewById<TextView>(com.example.common.R.id.textView130)
        textView129.text = title
        textView130.text = content
        sureButton.clickNoRepeat {
            when (title) {
                "退出登录" -> {
                    ClearCache.cleanApplicationData(Objects.requireNonNull(getApplication()))
                    activity.startActivity(Intent(activity, LoginActivity::class.java))
                    activity.finish()
                }
                "清除缓存" -> {
                    ClearCache.clearAllCache(Objects.requireNonNull(getApplication()))
                    cacheSize.value =
                        "0K"
                }
                else -> {
                }
            }
            dialogCircle.dismiss()
        }
        closeIv.clickNoRepeat {
            dialogCircle.dismiss()
        }
    }

    fun showDialogBottom(takephoto: TakePhoto, activity: Activity, flag: String, layout: Int): Unit {
        val rootView =
            View.inflate(activity, layout, null)
        dialogCircle =
            getPop(
                activity,
                rootView,
                1,
                3,
                Gravity.BOTTOM,
                R.style.BottomDialog_Animation,
                false,
                0,
                0
            )
        dialogCircle.show()
        val photograph_tv = rootView.findViewById<TextView>(R.id.photograph_tv)
        val photo_tv = rootView.findViewById<TextView>(R.id.photo_tv)
        val cancel_tv = rootView.findViewById<TextView>(R.id.cancel_tv)

        when (flag) {
            ONE -> {
                val file = File(
                    Environment.getExternalStorageDirectory(),
                    "/temp/" + System.currentTimeMillis() + ".jpg"
                )
                if (!file.parentFile.exists()) {
                    file.parentFile.mkdirs()
                }
                var imageUri = Uri.fromFile(file)
                //压缩设置
                //压缩设置
                val config: CompressConfig = CompressConfig.Builder().setMaxSize(102400)
                    .setMaxPixel(800)
                    .enableReserveRaw(true)
                    .create()
                takephoto.onEnableCompress(config, false)
                //拍照角度 使用takephoto自带相册
                //拍照角度 使用takephoto自带相册
                val builder: TakePhotoOptions.Builder =TakePhotoOptions. Builder()
                builder.setWithOwnGallery(false)
                builder.setCorrectImage(true)
                takephoto.setTakePhotoOptions(builder.create())

                //裁剪设置


                //裁剪设置
               val cropBuilder = CropOptions. Builder()
                cropBuilder.setOutputX(800).setOutputY(800)
                cropBuilder.setWithOwnCrop(true)


                photograph_tv.text = getApplication<Application>().getString(R.string.photograph)
                photo_tv.text = getApplication<Application>().getString(R.string.album)
                //拍照
                photograph_tv.clickNoRepeat {
                    takephoto.onPickFromCaptureWithCrop(imageUri, cropBuilder.create());
                    dialogCircle.dismiss()

                }
                //相册
                photo_tv.clickNoRepeat {
                    takephoto.onPickFromGalleryWithCrop(imageUri, cropBuilder.create());
                    dialogCircle.dismiss()
                }
            }
            TWO -> {
                photograph_tv.text = getApplication<Application>().getString(R.string.man)
                photo_tv.text = getApplication<Application>().getString(R.string.woman)
                //男
                photograph_tv.clickNoRepeat {
                    sex.value = photograph_tv.text as String
                    dialogCircle.dismiss()
                }
                //女
                photo_tv.clickNoRepeat {
                    sex.value = photo_tv.text as String
                    dialogCircle.dismiss()
                }
            }
            else -> {
            }
        }
        cancel_tv.clickNoRepeat {
            dialogCircle.dismiss()
        }
    }

    fun getCache(): MutableLiveData<String> {
        cacheSize = MutableLiveData()
        cacheSize.value = ClearCache.getTotalCacheSize(Objects.requireNonNull(getApplication()))
        return cacheSize
    }

    fun getProblem(page: String): Unit {
        val commitParam = CommitParam()
        commitParam.page = page
        commitParam.pageSize = "10"
        noHttpRx.postHttpJson(
            map,
            "常见问题",
            BaseUrl.BASE_URL3 + BaseUrl.PROBLEM,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }

    fun getInformation(): Unit {
        val commitParam = CommitParam()
        noHttpRx.postHttpJson(
            map,
            "个人资料",
            BaseUrl.BASE_URL3 + BaseUrl.INGORMATION,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }

}