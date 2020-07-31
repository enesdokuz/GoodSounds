package com.enesdokuz.goodsounds.ui.splash.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.enesdokuz.goodsounds.R
import java.util.*
import kotlin.concurrent.schedule

class SplashFragment : Fragment() {

    companion object {
        fun newInstance() =
            SplashFragment()
    }

    private val mHandler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Timer().schedule(2000) {
            mHandler.post {
                openHome()
            }
        }
    }

    private fun openHome() {
        findNavController().navigate(R.id.action_splashFragment_to_categoryFragment)
    }

}