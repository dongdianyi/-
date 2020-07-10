package com.example.smartagriculture.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.example.common.ToastUtil
import com.example.common.base.BaseFragment
import com.example.common.data.Identification.Companion.ONE
import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.FragmentMainBinding
import com.example.smartagriculture.util.init
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import me.jessyan.autosize.internal.CancelAdapt

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>(), CancelAdapt {

    var tempTime: Long = 2000
    var firstTime: Long = 0
    var fragments = arrayListOf<Fragment>()
    private val homeFragment: HomeFragment by lazy { HomeFragment() }
    private val chatFragment: ChatFragment by lazy { ChatFragment() }
    private val attendanceFragment: AttendanceFragment by lazy { AttendanceFragment() }
    private val distributionFragment: DistributionFragment by lazy { DistributionFragment() }
    private val mineFragment: MineFragment by lazy { MineFragment() }


    init {
        fragments.apply {
            add(homeFragment)
            add(chatFragment)
            add(attendanceFragment)
            add(distributionFragment)
            add(mineFragment)
        }
    }

    override fun initLayout(): Int {
        return R.layout.fragment_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(
            requireActivity(),
            SavedStateViewModelFactory(requireActivity().application, this)
        ).get(MainViewModel::class.java)
        dataBinding.data = viewModel
        //初始化viewpager2
        dataBinding.mainViewpager.init(this, fragments, false).run {
            offscreenPageLimit = fragments.size
        }
        //初始化 bottombar
        dataBinding.bottomNavigation.run {
            enableAnimation(false)
            enableShiftingMode(false)
            enableItemShiftingMode(false)
            setTextSize(12F)
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_main -> main_viewpager.setCurrentItem(0, false)
                    R.id.menu_chat -> main_viewpager.setCurrentItem(1, false)
                    R.id.menu_attendance -> main_viewpager.setCurrentItem(2, false)
                    R.id.menu_distribution -> main_viewpager.setCurrentItem(3, false)
                    R.id.menu_mine -> main_viewpager.setCurrentItem(4, false)
                }
                true
            }


        }
        if (ONE == arguments?.getString("bottomId").toString())
            dataBinding.bottomNavigation.selectedItemId = R.id.menu_chat


        //系统返回键的处理
        requireActivity().onBackPressedDispatcher.addCallback(this, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val secondTime = System.currentTimeMillis()
                if (secondTime - firstTime > 2000) {
                    ToastUtil("再按一次退出程序")
                    firstTime = secondTime
                } else {
                    requireActivity().finish()
                }
            }

        })
    }

    override fun lazyLoadData() {
    }
}
