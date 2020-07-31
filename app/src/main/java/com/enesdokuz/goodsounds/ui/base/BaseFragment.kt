package com.enesdokuz.goodsounds.ui.base

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {


    abstract fun initUI()
    abstract fun observeLiveData()
}