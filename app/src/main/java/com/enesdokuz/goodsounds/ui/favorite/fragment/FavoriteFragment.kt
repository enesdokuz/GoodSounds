package com.enesdokuz.goodsounds.ui.favorite.fragment

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.enesdokuz.goodsounds.R
import com.enesdokuz.goodsounds.ui.base.BaseFragment
import com.enesdokuz.goodsounds.ui.favorite.adapter.FavoriteAdapter
import com.enesdokuz.goodsounds.ui.favorite.viewmodel.FavoriteViewModel
import com.enesdokuz.goodsounds.ui.sound.listener.SoundListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : BaseFragment(), SoundListener {

    private val viewModel: FavoriteViewModel by activityViewModels()
    private val favoriteAdapter = FavoriteAdapter(arrayListOf(), this)
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initUI()
        observeLiveData()
        viewModel.getFavorites()
    }

    override fun initUI() {
        viewModel.isLoading.value = false
        recyclerFavorite.setHasFixedSize(true)
        recyclerFavorite.isNestedScrollingEnabled = false
        recyclerFavorite.layoutManager = LinearLayoutManager(context)
        recyclerFavorite.adapter = favoriteAdapter
    }

    override fun observeLiveData() {
        viewModel.favorites.observe(viewLifecycleOwner, Observer {
            it?.let {
                favoriteAdapter.update(it)
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    progressFavorite.visibility = View.VISIBLE
                } else {
                    progressFavorite.visibility = View.GONE
                }
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                Snackbar.make(rlMainFavorite, "$it", Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.tryAgain), View.OnClickListener {
                        viewModel.getFavorites()
                    }).show()
            }
        })
    }

    override fun onClickedFav(itemId: String, isFavorite: Boolean) {
        viewModel.setFavorite(soundId = itemId, isFavorite = false)
    }

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
