package com.example.smartagriculture.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.common.base.BaseFragment
import com.example.common.model.NoHttpRx
import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.FragmentGroupPersonBinding
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.ChatViewModel
import kotlinx.android.synthetic.main.title_item_two.*

/**
 * A simple [Fragment] subclass.
 */
class GroupPersonFragment : BaseFragment<ChatViewModel, FragmentGroupPersonBinding>() {

    lateinit var chatUser: String
    override fun initLayout(): Int {
        return R.layout.fragment_group_person
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(ChatViewModel::class.java)
        textView.text = getString(R.string.group_person)
        chatUser = arguments?.getString("chatUser").toString()

    }

    override fun lazyLoadData() {
        viewModel.noHttpRx = NoHttpRx(this)
        viewModel.getGroupPerson(chatUser)
    }

    override fun setListener() {
        back.setOnClickListener {
            nav().navigateUp()
        }
    }

    override fun toData(flag: String?, `object`: String?) {
        super.toData(flag, `object`)
    }
}
