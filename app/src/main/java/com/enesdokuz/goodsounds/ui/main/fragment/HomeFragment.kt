package com.enesdokuz.goodsounds.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.enesdokuz.goodsounds.R
import com.enesdokuz.goodsounds.ui.base.BaseFragment

class HomeFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initUI() {
        TODO("Not yet implemented")
    }

    override fun observeLiveData() {
        TODO("Not yet implemented")
    }

    override fun initController() {
        TODO("Not yet implemented")
    }
}