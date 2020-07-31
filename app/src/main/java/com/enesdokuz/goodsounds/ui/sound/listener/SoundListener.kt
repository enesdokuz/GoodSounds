package com.enesdokuz.goodsounds.ui.sound.listener

interface SoundListener {

    fun onClickedFav(itemId: String, isFavorite: Boolean)
    fun onClickedPlay(itemId: String, isPlaying: Boolean, mediaLink: String)
    fun onChangedVolume(itemId: String, soundLevel: Float)
}