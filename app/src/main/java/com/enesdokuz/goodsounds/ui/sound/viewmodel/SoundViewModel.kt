package com.enesdokuz.goodsounds.ui.sound.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.enesdokuz.goodsounds.model.Sound
import com.enesdokuz.goodsounds.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class SoundViewModel(application: Application) : BaseViewModel(application) {

    val categoryId = MutableLiveData<String>()
    val sounds = MutableLiveData<List<Sound>>()
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun getSounds() {
        showLoading()
        categoryId.value?.let {
            getSoundsFromRoom(it)
        }
    }

    private fun getSoundsFromRoom(categoryId: String) {
        launch {
            sounds.value = dao.getSounds(categoryId = categoryId)
        }
        if (sounds.value.isNullOrEmpty()) {
            getSoundsFromRetrofit()
        }
    }

    private fun getSoundsFromRetrofit() {
        disposable.add(
            service.getSounds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    result?.let { it ->
                        saveSoundsToRoom(it)
                        sounds.value = it.filter { it.categoryId == categoryId.value }
                        hideLoading()
                    }
                }, { error ->
                    error?.let {
                        showError(it.localizedMessage)
                        println(error)
                    }

                })
        )
    }

    fun setFavorite(soundId: String, isFavorite: Boolean) {
        launch {
            dao.updateSoundFav(id = soundId, isFavorite = isFavorite)
        }
    }

    fun setSoundVolume(soundId: String, volume: Float) {
        launch {
            dao.updateSoundVolume(id = soundId, volume = volume)
        }
    }

    private fun saveSoundsToRoom(list: List<Sound>) {
        launch {
            dao.insertSounds(*list.toTypedArray())
        }
    }

    private fun showError(errorMessage: String) {
        isLoading.value = false
        this.errorMessage.value = errorMessage
    }

    private fun showLoading() {
        isLoading.value = true
    }

    private fun hideLoading() {
        isLoading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}