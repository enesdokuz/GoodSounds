package com.enesdokuz.goodsounds.ui.sound.fragment

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.enesdokuz.goodsounds.R
import com.enesdokuz.goodsounds.ui.base.BaseFragment
import com.enesdokuz.goodsounds.ui.sound.adapter.SoundAdapter
import com.enesdokuz.goodsounds.ui.sound.listener.SoundListener
import com.enesdokuz.goodsounds.ui.sound.viewmodel.SoundViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_sounds.*

class SoundsFragment : BaseFragment(), SoundListener {

    private val viewModel: SoundViewModel by activityViewModels()
    private val soundAdapter = SoundAdapter(arrayListOf(), this)
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sounds, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initUI()
        observeLiveData()
        arguments?.let {
            requireArguments().getString("categoryId").let {
                viewModel.categoryId.value = it
                viewModel.getSounds()
            }
        }
    }

    override fun initUI() {
        viewModel.isLoading.value = false
        recyclerSound.setHasFixedSize(true)
        recyclerSound.isNestedScrollingEnabled = false
        recyclerSound.layoutManager = LinearLayoutManager(context)
        recyclerSound.adapter = soundAdapter
    }

    override fun observeLiveData() {
        viewModel.sounds.observe(viewLifecycleOwner, Observer {
            it?.let {
                soundAdapter.update(it)
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    progressSound.visibility = View.VISIBLE
                } else {
                    progressSound.visibility = View.GONE
                }
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                Snackbar.make(rlMainSound, "$it", Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.tryAgain), View.OnClickListener {
                        viewModel.getSounds()
                    }).show()
            }
        })
    }

    override fun onClickedFav(itemId: String, isFavorite: Boolean) {
        viewModel.setFavorite(soundId = itemId, isFavorite = isFavorite)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onClickedPlay(itemId: String, isPlaying: Boolean, mediaLink: String) {
        val uri = Uri.parse(mediaLink)
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            context?.let { setDataSource(it, uri) }
            prepare()
            if (isPlaying)
                stop()
            else
                start()

        }
    }

    override fun onChangedVolume(itemId: String, soundLevel: Float) {
        mediaPlayer?.setVolume(soundLevel, soundLevel)
        viewModel.setSoundVolume(soundId = itemId,volume = soundLevel)
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.let {
            it.stop()
        }
    }
}
